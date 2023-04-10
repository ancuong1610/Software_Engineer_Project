package de.hda.fbi.efussgaengerzone.domain.usecase.abstractions;


public interface SecurePasswordValidator {
    boolean isPasswordSecure(String password);
}
