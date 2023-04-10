package de.hda.fbi.efussgaengerzone.domain.model.shop;

import java.util.Set;
import java.util.UUID;

public record Shop(
        UUID id,
        String name,
        String description,
        Set<VideoMessenger> supportedVideoMessengers,
        WeeklyOpeningHours weeklyOpeningHours,
        Set<Tag> tags,
        Boolean active,
        Integer minsPerCustomer,
        String ownerEmail
) {}
