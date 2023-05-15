package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.ShopRepository;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Tag;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ShopBrowsing {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ShopBrowsing.class);
    private final ShopRepository shopRepository;

    public ShopBrowsing(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop getShopByOwner(String ownerEmail) {
        Set<Shop> shops = shopRepository.findPredicate(shop -> shop.ownerEmail().equals(ownerEmail));
        if (shops.isEmpty()) {
            throw new ShopNotFoundException(ownerEmail);
        }
        return shops.stream().findFirst().orElseThrow();
    }

    public Set<Shop> findAll() {
        return Set.of();
    }

    public Set<Shop> findShopsByQuery(Set<String> words, Set<Tag> tags) {
        return Set.of();
    }

    public Optional<Shop> findShopById(UUID uuid) {
        log.info("looking up shop for id {}", uuid);
        return shopRepository.findById(uuid);
    }

    public Set<Shop> getAllShops() {
        log.info("finding all shops");
        return shopRepository.findAll();
    }


}
