package de.hda.fbi.efussgaengerzone.adapter;

import de.hda.fbi.efussgaengerzone.domain.usecase.abstractions.SecurePasswordValidator;
import org.passay.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SecurePasswordValidatorImpl implements SecurePasswordValidator {
    @Override
    public boolean isPasswordSecure(String password) {
        PasswordValidator validator = new PasswordValidator(
                new org.passay.LengthRule(8, 16),
                new org.passay.CharacterRule(GermanCharacterData.UpperCase, 1),
                new org.passay.CharacterRule(GermanCharacterData.LowerCase, 1),
                new org.passay.CharacterRule(EnglishCharacterData.Digit, 1),
                new WhitespaceRule(),
                new IllegalSequenceRule(GermanSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                new RepeatCharactersRule(3, 1),
                new IllegalSequenceRule(GermanSequenceData.DEQwertz, 5, false)
        );
        return validator.validate(new PasswordData(password)).isValid();
    }
}