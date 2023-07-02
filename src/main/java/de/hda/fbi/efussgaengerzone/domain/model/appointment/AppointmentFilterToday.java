package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import java.time.LocalDate;
public class AppointmentFilterToday implements AppointmentFilter {

    public static final AppointmentFilterToday INSTANCE = new AppointmentFilterToday();

    private AppointmentFilterToday() {

    }

    // Zu implementieren in Praktikum 6
    @Override
    public boolean test(Appointment appointment) {
        return appointment.dateTime().getDayOfMonth() == LocalDate.now().getDayOfMonth()
                && appointment.dateTime().getMonth() == LocalDate.now().getMonth()
                && appointment.dateTime().getYear() == LocalDate.now().getYear();
    }
}
