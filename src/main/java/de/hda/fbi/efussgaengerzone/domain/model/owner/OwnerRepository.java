package de.hda.fbi.efussgaengerzone.domain.model.owner;

import java.util.Set;
import java.util.function.Predicate;

public interface OwnerRepository {

    void save(Owner owner);

    Set<Owner> findPredicate(Predicate<Owner> ownerPredicate);

    Owner find(String email);
}
