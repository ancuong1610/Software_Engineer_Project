package de.hda.fbi.efussgaengerzone.views.model;

import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Tag;
import de.hda.fbi.efussgaengerzone.domain.model.shop.VideoMessenger;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ShopDto(
        UUID id,
        String name,
        String description,
        Set<String> tags,
        List<VideoMessengerDto> messengers,
        WeekdayListDto<OpeningHoursDto> openingHours,
        Integer minsPerCustomer
) {

    public static ShopDto fromShop(Shop shop) {
        return new ShopDto(
                shop.id(),
                shop.name(),
                shop.description(),
                shop.tags().stream()
                        .map(Tag::value).collect(Collectors.toSet()),
                Arrays.stream(VideoMessenger.values()).map(m ->
                        new VideoMessengerDto(m.name(), m.label, shop.supportedVideoMessengers().contains(m))
                ).toList(),
                WeekdayListDto.fromWeeklyOpeningHours(shop.weeklyOpeningHours()),
                shop.minsPerCustomer()
        );
    }

    public static List<ShopDto> fromShops(Collection<Shop> shops) {
        return shops.stream().map(ShopDto::fromShop).toList();
    }
}

