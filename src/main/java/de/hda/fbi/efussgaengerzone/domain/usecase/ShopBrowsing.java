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

    /*
    es wird ein leeres Ergebnis zurückgegeben, weil kein Shop zu den Filtern passt
    es werden ein oder mehrere Shops zurückgegeben, die zu den Filtern passen (überprüfen Sie hier mehrere
    Kombination von Filtern)
     */
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

    /*
    es wird eine leere Liste zurückgegeben, wenn keine Shops existieren
    es werden alle Shops zurückgegeben
     */
    public Set<Shop> findAll() {
        log.info("finding all shops");
        return shopRepository.findAll();
    }

    public Set<Shop> findShopsByQuery(Set<String> words, Set<Tag> tags) {
        log.info("finding all shops with words {} and tags {}", words, tags);

        if (tags.isEmpty()) {
            log.error("no words or tags provided for shop search");
            throw new IllegalArgumentException("no words or tags provided");
        }

        return shopRepository.findPredicate(shop -> shopHasTags(shop, tags));
    }

    private boolean shopNameContainsWord(Shop shop, Set<String> words) {
        return words.stream()
                .anyMatch(word -> shop.name().toLowerCase().contains(word));
    }

    private boolean shopHasTags(Shop shop, Set<Tag> tags) {
        return tags.stream()
                .anyMatch(tag -> shop.tags().contains(tag));
    }

    /* ein Shop wird anhand der ID zurückgegeben
    der Shop mit der gewählten ID existiert nicht
     */
    public Optional<Shop> findShopById(UUID uuid) {
        log.info("looking up shop for id {}", uuid);
        return shopRepository.findById(uuid);
    }

}