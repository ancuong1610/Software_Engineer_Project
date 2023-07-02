package de.hda.fbi.efussgaengerzone.domain.usecase;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String email) {
        super(String.format("Owner will email '%s' does not exist.", email));
    }
}
