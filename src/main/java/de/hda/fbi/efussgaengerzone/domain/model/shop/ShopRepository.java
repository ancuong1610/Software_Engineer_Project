package de.hda.fbi.efussgaengerzone.domain.model.shop;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public interface ShopRepository {
    Optional<Shop> findById(UUID id);

    Set<Shop> findAll();

    Set<Shop> findPredicate(Predicate<Shop> predicate);

    Shop save(Shop toSave);

    void clear();
}
