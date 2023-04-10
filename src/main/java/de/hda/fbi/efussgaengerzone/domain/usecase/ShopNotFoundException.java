package de.hda.fbi.efussgaengerzone.domain.usecase;

import java.util.UUID;

public class ShopNotFoundException extends RuntimeException {
    public ShopNotFoundException(UUID shopId) {
        super(String.format("Shop with id '%s' not found", shopId));
    }

    public ShopNotFoundException(String email) {
        super(String.format("No shop found for owner with email '%s'", email));
    }
}
