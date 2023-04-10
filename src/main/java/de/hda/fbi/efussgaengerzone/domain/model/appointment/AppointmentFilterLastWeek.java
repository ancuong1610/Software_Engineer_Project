package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class AppointmentFilterLastWeek implements AppointmentFilter {

    public static final AppointmentFilterLastWeek INSTANCE = new AppointmentFilterLastWeek();

    private AppointmentFilterLastWeek() {

    }

    @Override
    public boolean test(Appointment appointment) {
        return appointment.dateTime().toLocalDate()
                .isAfter(LocalDate.now().minusDays(7).with(ChronoField.DAY_OF_WEEK, 1).minusDays(1)) && // minusDays(1) = isEqualOrAfter
                appointment.dateTime().toLocalDate().isBefore(LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1));
    }
}
