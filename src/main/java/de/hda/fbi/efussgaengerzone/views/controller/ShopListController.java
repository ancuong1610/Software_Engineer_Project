package de.hda.fbi.efussgaengerzone.views.controller;

import de.hda.fbi.efussgaengerzone.domain.model.shop.Tag;
import de.hda.fbi.efussgaengerzone.domain.usecase.ShopBrowsing;
import de.hda.fbi.efussgaengerzone.views.model.ShopDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ShopListController {

    private final ShopBrowsing shopBrowsing;

    public ShopListController(ShopBrowsing shopBrowsing) {
        this.shopBrowsing = shopBrowsing;
    }

    @GetMapping("/")
    public ModelAndView listView(@ModelAttribute("searchText") String searchText) {
        List<ShopDto> shops;
        if (searchText == null || searchText.equals("")) {
            shops = ShopDto.fromShops(shopBrowsing.findAll());
        } else {

            var tags = Stream.of(searchText.split(" "))
                    .filter(w -> w.startsWith("#"))
                    .map(w -> Tag.of(w.substring(1)))
                    .collect(Collectors.toSet());

            var words = Stream.of(searchText.split(" "))
                    .filter(w -> !w.startsWith("#"))
                    .collect(Collectors.toSet());

            shops = ShopDto.fromShops(shopBrowsing.findShopsByQuery(words, tags));
        }

        return new ModelAndView("shopList", ViewConstants.MODEL_NAME, new ListViewModel(
                shops,
                searchText
        ));
    }

    record ListViewModel(List<ShopDto> shops, String searchText) {
    }


}
