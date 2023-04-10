package de.hda.fbi.efussgaengerzone.views.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.Appointment;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.VideoMessenger;
import de.hda.fbi.efussgaengerzone.domain.usecase.AppointmentScheduling;
import de.hda.fbi.efussgaengerzone.domain.usecase.ShopBrowsing;
import de.hda.fbi.efussgaengerzone.domain.usecase.ShopNotFoundException;
import de.hda.fbi.efussgaengerzone.views.model.ShopDto;
import de.hda.fbi.efussgaengerzone.views.model.WeekdayListDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shops")
public class ShopController {

    private final ShopBrowsing shopBrowsing;
    private final AppointmentScheduling appointmentScheduling;
    private final ObjectMapper objectMapper;

    public ShopController(ShopBrowsing shopBrowsing, AppointmentScheduling appointmentScheduling, ObjectMapper objectMapper) {
        this.shopBrowsing = shopBrowsing;
        this.appointmentScheduling = appointmentScheduling;
        this.objectMapper = objectMapper;

    }

    @GetMapping("/{id}")
    public ModelAndView shopView(@PathVariable("id") UUID shopId) {
        Optional<Shop> shop = shopBrowsing.findShopById(shopId);
        if (shop.isEmpty()) {
            throw new ShopNotFoundException(shopId);
        }
        return new ModelAndView("shopDetails", ViewConstants.MODEL_NAME, new ShopViewModel(
                ShopDto.fromShop(shop.get())
        ));
    }

    record ShopViewModel(ShopDto shop) {
    }

    @GetMapping("/{id}/book")
    public ModelAndView bookingView(@PathVariable("id") UUID shopId) throws JsonProcessingException {
        Optional<Shop> shop = shopBrowsing.findShopById(shopId);
        if (shop.isEmpty()) {
            throw new ShopNotFoundException(shopId);
        }

        List<DayOfWeek> weekdays = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
        Map<DayOfWeek, List<LocalTime>> availableTimes = weekdays.stream().collect(Collectors.toMap(day -> day, day -> appointmentScheduling.availableDatesOnDay(shop.get().id(), day)));

        return new ModelAndView("shopBooking", ViewConstants.MODEL_NAME, new BookingViewModel(
                ShopDto.fromShop(shop.get()),
                WeekdayListDto.fromBookingTimes(availableTimes),
                objectMapper.writeValueAsString(availableTimes)
        ));
    }

    record BookingViewModel(ShopDto shop,
                            WeekdayListDto<List<LocalTime>> availableTimes,
                            String availableTimesJson) {
    }

    @PostMapping("/{id}/appointments")
    public ModelAndView bookAppointment(@PathVariable("id") UUID shopId,
                                        @RequestParam("name") String name,
                                        @RequestParam("contact") String contact,
                                        @RequestParam("messenger") VideoMessenger messenger,
                                        @RequestParam("weekday") DayOfWeek weekday,
                                        @RequestParam("time") LocalTime time) {
        Optional<Shop> shop = shopBrowsing.findShopById(shopId);
        if (shop.isEmpty()) {
            throw new ShopNotFoundException(shopId);
        }

        appointmentScheduling.makeAppointment(shopId, new Appointment(
                UUID.randomUUID(),
                name,
                messenger,
                contact,
                LocalDate.now().with(TemporalAdjusters.next(weekday)).atTime(time),
                Duration.ofMinutes(shop.get().minsPerCustomer())
        ));

        return new ModelAndView("shopBookingSuccess");
    }
}
