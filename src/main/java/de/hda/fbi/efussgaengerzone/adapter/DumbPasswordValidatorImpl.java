package de.hda.fbi.efussgaengerzone.adapter;

import de.hda.fbi.efussgaengerzone.domain.usecase.abstractions.SecurePasswordValidator;
import org.springframework.stereotype.Component;

@Component
public class DumbPasswordValidatorImpl implements SecurePasswordValidator {
    @Override
    public boolean isPasswordSecure(String password) {
        return true;
    }
}
