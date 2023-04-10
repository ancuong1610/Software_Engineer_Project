package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import java.time.LocalDate;

public class AppointmentFilterPast implements AppointmentFilter {

    public static final AppointmentFilterPast INSTANCE = new AppointmentFilterPast();

    private AppointmentFilterPast() {
    }

    @Override
    public boolean test(Appointment appointment) {
        return appointment.dateTime().toLocalDate().isBefore(LocalDate.now());
    }
}
