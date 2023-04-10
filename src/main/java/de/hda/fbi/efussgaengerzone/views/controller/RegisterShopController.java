package de.hda.fbi.efussgaengerzone.views.controller;

import de.hda.fbi.efussgaengerzone.domain.model.owner.Owner;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Tag;
import de.hda.fbi.efussgaengerzone.domain.model.shop.VideoMessenger;
import de.hda.fbi.efussgaengerzone.domain.usecase.InsecurePasswordException;
import de.hda.fbi.efussgaengerzone.domain.usecase.OwnerRegistration;
import de.hda.fbi.efussgaengerzone.domain.usecase.ShopOrganization;
import de.hda.fbi.efussgaengerzone.views.model.ShopDto;
import de.hda.fbi.efussgaengerzone.views.model.WeekdayListDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static de.hda.fbi.efussgaengerzone.views.mapper.WeeklyOpeningHoursMapper.mapWeeklyOpeningHours;

@Controller
@RequestMapping("registration")
public class RegisterShopController {

    private final ShopOrganization shopOrganization;
    private final OwnerRegistration ownerRegistration;

    public RegisterShopController(ShopOrganization shopOrganization, OwnerRegistration ownerRegistration) {
        this.shopOrganization = shopOrganization;
        this.ownerRegistration = ownerRegistration;
    }

    @GetMapping
    public ModelAndView registrationView() {
        return new ModelAndView("registerShop", ViewConstants.MODEL_NAME, new RegistrationViewModel());
    }

    class RegistrationViewModel {
        // the registration view re-uses the edit-shop view for defining the shop opening hours. As a consequence of this,
        // the model needs an empty list of opening hours.
        ShopDto shop = new ShopDto(null, null, null, null, null, WeekdayListDto.empty(), null);
    }

    @PostMapping
    public ModelAndView register(@RequestParam("account[name]") String accountName,
                                 @RequestParam("account[email]") String accountEmail,
                                 @RequestParam("account[password]") String accountPassword,
                                 @RequestParam("shop[name]") String shopName,
                                 @RequestParam("shop[description]") String shopDescription,
                                 @RequestParam("shop[messengers]") Set<String> shopMessengers,
                                 @RequestParam("shop[tags]") Set<String> shopTags,
                                 @RequestParam("shop[reservation-duration]") Integer minsPerCustomer,
                                 @RequestParam Map<String, String> allParams) {
        Owner registeredOwner;
        try {
            registeredOwner = ownerRegistration.register(new Owner(accountName, accountEmail, accountPassword));
        } catch (InsecurePasswordException e) {
            return registrationView().addObject("passwordError", true);
        }

        Shop shop = new Shop(UUID.randomUUID(),
                shopName,
                shopDescription,
                shopMessengers.stream().map(VideoMessenger::valueOf).collect(Collectors.toSet()),
                mapWeeklyOpeningHours(allParams),
                shopTags.stream().map(Tag::new).collect(Collectors.toSet()),
                true,
                minsPerCustomer,
                registeredOwner.email()
        );
        shopOrganization.createShop(shop);

        return new ModelAndView("registerShopSuccess");
    }

}
