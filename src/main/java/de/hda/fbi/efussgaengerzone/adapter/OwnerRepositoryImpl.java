package de.hda.fbi.efussgaengerzone.adapter;

import de.hda.fbi.efussgaengerzone.domain.model.owner.Owner;
import de.hda.fbi.efussgaengerzone.domain.model.owner.OwnerRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class OwnerRepositoryImpl implements OwnerRepository {

    private final Map<String, Owner> ownersByEmail = new HashMap<>();

    @Override
    public void save(Owner owner) {
        ownersByEmail.put(owner.email(), owner);
    }

    @Override
    public Set<Owner> findPredicate(Predicate<Owner> ownerPredicate) {
        return ownersByEmail.values().stream().filter(ownerPredicate).collect(Collectors.toSet());
    }

    @Override
    public Owner find(String email) {
        return ownersByEmail.get(email);
    }
}
