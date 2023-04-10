package de.hda.fbi.efussgaengerzone.domain.model.shop;

import java.time.LocalTime;

public record OpeningHours(
        LocalTime openingTime,
        LocalTime closingTime
) {}
