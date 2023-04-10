package de.hda.fbi.efussgaengerzone.domain.model.shop;

import java.util.Locale;

public record Tag(String value) {

    public static Tag of(String value) {
        if (value.contains(" ")) {
            throw new IllegalArgumentException("tags may only consist of a single word and may not contain whitespace");
        }
        if (!value.toLowerCase(Locale.ROOT).equals(value)) {
            throw new IllegalArgumentException("tags may not contain upper letters");
        }

        return new Tag(value);
    }
}
