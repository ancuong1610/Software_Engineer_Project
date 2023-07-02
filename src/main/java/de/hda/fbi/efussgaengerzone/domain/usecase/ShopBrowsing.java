package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.ShopRepository;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Tag;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

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

    // Zu Implementieren in Praktikum 4
    public Set<Shop> findAll() {
        log.info("finding all shops");
        return shopRepository.findAll();
    }

    // Zu Implementieren in Praktikum 4
    public Set<Shop> findShopsByQuery(Set<String> words, Set<Tag> tags) {
        log.info("finding all shops with words {} and tags {}", words, tags);

        if (words.isEmpty() && tags.isEmpty()) {
            log.error("no words or tags provided for shop search");
            throw new IllegalArgumentException("no words or tags provided for shop search");
        }

        var wordsLower = words.stream()
                .map(String::toLowerCase)
                .collect(toSet());

        return shopRepository.findPredicate(shop -> shopNameContainsWord(shop, wordsLower) || shopHasTags(shop, tags));
    }

    // Zu Implementieren in Praktikum 4
    // Für Implementierungsrahmen vollständig zu entfernen
    private boolean shopNameContainsWord(Shop shop, Set<String> words) {
        return words.stream()
                .anyMatch(word -> shop.name().toLowerCase().contains(word));
    }

    // Zu Implementieren in Praktikum 4
    // Für Implementierungsrahmen vollständig zu entfernen
    private boolean shopHasTags(Shop shop, Set<Tag> tags) {
        return tags.stream()
                .anyMatch(tag -> shop.tags().contains(tag));
    }

    /**
     *
     * @param uuid eindeutige ID
     * @return Shop-Referenz, falls diese vorhanden ist.
     */
    public Optional<Shop> findShopById(UUID uuid) {
        log.info("looking up shop for id {}", uuid);
        return shopRepository.findById(uuid);
    }

}
