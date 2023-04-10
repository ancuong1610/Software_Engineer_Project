package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import de.hda.fbi.efussgaengerzone.domain.model.shop.VideoMessenger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record Appointment(
        UUID id,
        String customerName,
        VideoMessenger chosenMessenger,
        String videoMessengerContact,
        LocalDateTime dateTime, Duration duration
) {
}
