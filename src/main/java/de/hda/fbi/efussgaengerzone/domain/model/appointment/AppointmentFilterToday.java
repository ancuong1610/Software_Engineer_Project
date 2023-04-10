package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import java.time.LocalDate;

public class AppointmentFilterToday implements AppointmentFilter {

    public static final AppointmentFilterToday INSTANCE = new AppointmentFilterToday();

    private AppointmentFilterToday() {

    }

    @Override
    public boolean test(Appointment appointment) {
        return appointment.dateTime().toLocalDate().isEqual(LocalDate.now());
    }
}
