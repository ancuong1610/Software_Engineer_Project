package de.hda.fbi.efussgaengerzone.adapter;

import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.ShopRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ShopRepositoryImpl implements ShopRepository {

    private final HashMap<UUID, Shop> shopsById = new HashMap<>();

    @Override
    public Optional<Shop> findById(UUID id) {
        return Optional.ofNullable(shopsById.get(id));
    }

    @Override
    public Set<Shop> findAll() {
        return new HashSet<>(shopsById.values());
    }

    @Override
    public Set<Shop> findPredicate(Predicate<Shop> predicate) {
        return shopsById.values().stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Override
    public Shop save(Shop toSave) {
        shopsById.put(toSave.id(), toSave);
        return toSave;
    }

    @Override
    public void clear() {
        shopsById.clear();
    }
}
