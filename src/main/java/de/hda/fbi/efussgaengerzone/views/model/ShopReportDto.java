package de.hda.fbi.efussgaengerzone.views.model;

import de.hda.fbi.efussgaengerzone.domain.model.reporting.ShopReport;

public record ShopReportDto(
        long appointmentsLastWeek,
        long appointmentsCurrentWeek,
        long appointmentsNextWeek,
        VideoMessengerDto mostPopularMessenger,
        long totalCustomers
) {

    @SuppressWarnings("squid:S3655")
    // false-positive: Call "Optional#isPresent()" or "!Optional#isEmpty()" before accessing the value.
    public static ShopReportDto fromShopReport(ShopReport report) {
        return new ShopReportDto(
                report.appointmentsLastWeek(),
                report.appointmentsCurrentWeek(),
                report.appointmentsNextWeek(),
                report.mostPopularMessenger().isPresent() ? new VideoMessengerDto(report.mostPopularMessenger().get().name(), report.mostPopularMessenger().get().label, true) : null,
                report.totalCustomers()
        );
    }
}
