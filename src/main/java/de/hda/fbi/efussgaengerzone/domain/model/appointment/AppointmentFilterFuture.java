package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

public class AppointmentFilterFuture implements AppointmentFilter {

    public static final AppointmentFilterFuture INSTANCE = new AppointmentFilterFuture();

    private AppointmentFilterFuture() {

    }

    // Zu implementieren in Praktikum 6
    @Override
    public boolean test(Appointment appointment) {
        return appointment.dateTime().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()));
    }
}
