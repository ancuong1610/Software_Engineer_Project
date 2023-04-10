package de.hda.fbi.efussgaengerzone.views.model;

import de.hda.fbi.efussgaengerzone.domain.model.shop.OpeningHours;

import java.time.LocalTime;

public record OpeningHoursDto(LocalTime openingTime, LocalTime closingTime) {
    public static OpeningHoursDto fromOpeningHours(OpeningHours openingHours) {
        if (openingHours == null) {
            return null;
        }
        return new OpeningHoursDto(openingHours.openingTime(), openingHours.closingTime());
    }
}