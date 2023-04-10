package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopOrganization {

    final ShopRepository shopRepository;

    public ShopOrganization(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void createShop(Shop shop) {

    }

    public void updateShopEmail(UUID shopId, String newMail) {

    }

    @SuppressWarnings("squid:S2201")
    public void changeShop(Shop shop) {
        shopRepository.findById(shop.id())
                .orElseThrow(() -> new ShopNotFoundException(shop.id()));
        shopRepository.save(shop);
    }

}
