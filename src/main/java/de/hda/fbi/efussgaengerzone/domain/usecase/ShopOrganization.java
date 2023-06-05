package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.ShopRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopOrganization {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ShopOrganization.class);
    final ShopRepository shopRepository;

    public ShopOrganization(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    public void createShop(Shop shop) {
        log.debug("Create shop {}", shop);
        shopRepository.save(shop);
    }

    public void updateShopEmail(UUID shopId, String newMail) throws ShopNotFoundException {
        log.debug("changing shop with id {} to render {}", shopId, newMail);
        var shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));
        var shopWithNewEmail = new Shop(
                shop.id(),
                shop.name(),
                shop.description(),
                shop.supportedVideoMessengers(),
                shop.weeklyOpeningHours(),
                shop.tags(),
                shop.active(),
                shop.minsPerCustomer(),
                newMail
        );
        changeShop(shopWithNewEmail);
    }

    @SuppressWarnings("squid:S2201")
    public void changeShop(Shop shop) {
        shopRepository.findById(shop.id())
                .orElseThrow(() -> new ShopNotFoundException(shop.id()));
        shopRepository.save(shop);

    }

}
