package de.hda.fbi.efussgaengerzone.views.controller;

import de.hda.fbi.efussgaengerzone.domain.model.appointment.*;
import de.hda.fbi.efussgaengerzone.domain.model.owner.Owner;
import de.hda.fbi.efussgaengerzone.domain.model.reporting.ShopReport;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Tag;
import de.hda.fbi.efussgaengerzone.domain.model.shop.VideoMessenger;
import de.hda.fbi.efussgaengerzone.domain.usecase.*;
import de.hda.fbi.efussgaengerzone.views.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

import static de.hda.fbi.efussgaengerzone.views.controller.OwnerLoginController.COOKIE_NAME;
import static de.hda.fbi.efussgaengerzone.views.mapper.WeeklyOpeningHoursMapper.mapWeeklyOpeningHours;
import static de.hda.fbi.efussgaengerzone.views.model.AppointmentFilterTypeDto.*;

@Controller
@RequestMapping("owner")
public class OwnerController {
    private static final Comparator<Appointment> APPOINTMENT_COMPARATOR = Comparator.comparing(Appointment::dateTime).reversed();

    private final OwnerRegistration ownerRegistration;
    private final ShopBrowsing shopBrowsing;
    private final ShopOrganization shopOrganization;
    private final AppointmentScheduling appointmentScheduling;
    private final Reporting reporting;

    public OwnerController(OwnerRegistration ownerRegistration, ShopBrowsing shopBrowsing, ShopOrganization shopOrganization, AppointmentScheduling appointmentScheduling, Reporting reporting) {
        this.ownerRegistration = ownerRegistration;
        this.shopBrowsing = shopBrowsing;
        this.shopOrganization = shopOrganization;
        this.appointmentScheduling = appointmentScheduling;
        this.reporting = reporting;
    }

    @GetMapping
    public ModelAndView ownerView(HttpServletRequest request) {
        Owner owner = getOwner(request);
        Shop shop = shopBrowsing.getShopByOwner(owner.email());
        Optional<Appointment> nextAppointment = appointmentScheduling.findNextAppointment(shop.id());
        ShopReport report = reporting.getShopReport(shop.id());

        return new ModelAndView("ownerOverview", ViewConstants.MODEL_NAME, new OwnerViewModel(
                OwnerDto.fromOwner(owner),
                ShopDto.fromShop(shop),
                ShopReportDto.fromShopReport(report),
                nextAppointment.isPresent() ? AppointmentDto.fromAppointment(nextAppointment.get()) : null));
    }

    record OwnerViewModel(OwnerDto owner, ShopDto shop, ShopReportDto report, AppointmentDto nextAppointment) {
    }

    @GetMapping("/appointments")
    public ModelAndView ownerAppointmentsView(HttpServletRequest request,
                                              @RequestParam(value = "filter", defaultValue = "", required = false) List<AppointmentFilterTypeDto> filters) {
        Owner owner = getOwner(request);
        ShopDto shop = ShopDto.fromShop(shopBrowsing.getShopByOwner(owner.email()));

        Set<AppointmentFilter> filterPredicates = filters.stream().map(
                filter -> switch (filter) {
                    case PAST -> AppointmentFilterPast.INSTANCE;
                    case TODAY -> AppointmentFilterToday.INSTANCE;
                    default -> AppointmentFilterFuture.INSTANCE;
                }).collect(Collectors.toSet());

        List<AppointmentDto> appointments = appointmentScheduling.searchAppointments(shop.id(), filterPredicates)
                .stream()
                .sorted(APPOINTMENT_COMPARATOR)
                .map(AppointmentDto::fromAppointment)
                .toList();

        return new ModelAndView("ownerAppointments", ViewConstants.MODEL_NAME, new OwnerAppointmentsViewModel(
                OwnerDto.fromOwner(owner),
                appointments,
                filters.contains(PAST),
                flipFilterOptionAsString(filters, PAST),
                filters.contains(TODAY),
                flipFilterOptionAsString(filters, TODAY),
                filters.contains(FUTURE),
                flipFilterOptionAsString(filters, FUTURE)
        ));
    }

    String flipFilterOptionAsString(Collection<AppointmentFilterTypeDto> filters, AppointmentFilterTypeDto toFlip) {
        var newActiveOptions = new HashSet<>(filters);
        if (filters.contains(toFlip)) {
            newActiveOptions.remove(toFlip);
        } else {
            newActiveOptions.add(toFlip);
        }
        return newActiveOptions.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(","));
    }

    record OwnerAppointmentsViewModel(
            OwnerDto owner,
            List<AppointmentDto> appointments,
            boolean filterPastChecked, String flipPastFilterQueryValue,
            boolean filterTodayChecked, String flipTodayFilterQueryValue,
            boolean filterFutureChecked, String flipFutureFilterQueryValue
    ) {
    }

    @GetMapping("/shop-edit")
    public ModelAndView ownerEditShopView(HttpServletRequest request) {
        Owner owner = getOwner(request);
        Shop shop = shopBrowsing.getShopByOwner(owner.email());

        return new ModelAndView("ownerEditShop", ViewConstants.MODEL_NAME, new OwnerEditShopViewModel(
                OwnerDto.fromOwner(owner),
                ShopDto.fromShop(shop)));
    }

    record OwnerEditShopViewModel(OwnerDto owner, ShopDto shop) {
    }

    @GetMapping("/edit")
    public ModelAndView ownerEditOwnerView(HttpServletRequest request) {
        Owner owner = getOwner(request);

        return new ModelAndView("ownerEdit", ViewConstants.MODEL_NAME, new OwnerEditOwnerViewModel(
                OwnerDto.fromOwner(owner)));
    }

    record OwnerEditOwnerViewModel(OwnerDto owner) {
    }

    @PostMapping
    public ModelAndView updateOwner(@RequestParam("name") String newName,
                                    @RequestParam("email") String newEmail,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        Owner owner = getOwner(request);

        ownerRegistration.updateOwner(owner.email(), newEmail, newName);
        response.addCookie(new Cookie(COOKIE_NAME, newEmail));

        return new ModelAndView("redirect:/owner/edit");
    }

    @PostMapping("/appointments/{appointmentId}/delete")
    public ModelAndView ownerDeleteAppointment(@PathVariable UUID appointmentId,
                                               HttpServletRequest request) {
        Owner owner = getOwner(request);
        Shop shop = shopBrowsing.getShopByOwner(owner.email());

        appointmentScheduling.deleteAppointment(shop.id(), appointmentId);
        return ownerAppointmentsView(request, List.of());
    }

    @PostMapping("/shop")
    public ModelAndView updateShop(@RequestParam("shop[name]") String shopName,
                                   @RequestParam("shop[description]") String shopDescription,
                                   @RequestParam("shop[messengers]") Set<String> shopMessengers,
                                   @RequestParam("shop[tags]") Set<String> shopTags,
                                   @RequestParam("shop[reservation-duration]") Integer minsPerCustomer,
                                   @RequestParam Map<String, String> allParams,
                                   HttpServletRequest request) {

        Owner owner = getOwner(request);

        Shop shopByOwner = shopBrowsing.getShopByOwner(owner.email());
        Shop shop = new Shop(shopByOwner.id(),
                shopName,
                shopDescription,
                shopMessengers.stream().map(VideoMessenger::valueOf).collect(Collectors.toSet()),
                mapWeeklyOpeningHours(allParams),
                shopTags.stream().map(Tag::new).collect(Collectors.toSet()),
                true,
                minsPerCustomer,
                owner.email());

        shopOrganization.changeShop(shop);

        return new ModelAndView("redirect:/owner");
    }

    private Owner getOwner(HttpServletRequest request) {
        Optional<Cookie> authCookie = Arrays.stream(request.getCookies()).filter(c -> COOKIE_NAME.equals(c.getName()))
                .findFirst();

        if (authCookie.isEmpty()) {
            throw new IllegalStateException("No auth cookie set");
        }

        return ownerRegistration.findByEmail(authCookie.get().getValue());
    }
}
