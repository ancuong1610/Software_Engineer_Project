package de.hda.fbi.efussgaengerzone.domain.model.reporting;

import de.hda.fbi.efussgaengerzone.domain.model.shop.VideoMessenger;

import java.util.Optional;

public record ShopReport(
        long appointmentsLastWeek,
        long appointmentsCurrentWeek,
        long appointmentsNextWeek,
        Optional<VideoMessenger> mostPopularMessenger,
        long totalCustomers
) {
}
