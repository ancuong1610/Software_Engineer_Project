package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class AppointmentFilterNextWeek implements AppointmentFilter {

    public static final AppointmentFilterNextWeek INSTANCE = new AppointmentFilterNextWeek();

    private AppointmentFilterNextWeek() {

    }

    @Override
    public boolean test(Appointment appointment) {
        return appointment.dateTime().toLocalDate()
                .isBefore(LocalDate.now().plusDays(14).with(ChronoField.DAY_OF_WEEK, 1)) &&
                appointment.dateTime().toLocalDate().isAfter(LocalDate.now().plusDays(7).with(ChronoField.DAY_OF_WEEK, 1).minusDays(1)); // minusDays(1) = isEqualOrAfter
    }
}
