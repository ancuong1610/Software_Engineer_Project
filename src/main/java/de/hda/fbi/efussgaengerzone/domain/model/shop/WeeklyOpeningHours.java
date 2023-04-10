package de.hda.fbi.efussgaengerzone.domain.model.shop;

public record WeeklyOpeningHours(
        OpeningHours monday,
        OpeningHours tuesday,
        OpeningHours wednesday,
        OpeningHours thursday,
        OpeningHours friday,
        OpeningHours saturday
) {}
