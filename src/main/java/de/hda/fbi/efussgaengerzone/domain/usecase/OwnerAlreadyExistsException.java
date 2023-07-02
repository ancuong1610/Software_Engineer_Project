package de.hda.fbi.efussgaengerzone.domain.usecase;

public class OwnerAlreadyExistsException extends RuntimeException {
    public OwnerAlreadyExistsException(String email) {
        super(String.format("Owner will email '%s' already present. Please use a different email.", email));
    }
}
