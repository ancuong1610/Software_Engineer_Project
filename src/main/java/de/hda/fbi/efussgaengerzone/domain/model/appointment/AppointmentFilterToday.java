package de.hda.fbi.efussgaengerzone.domain.model.appointment;

public class AppointmentFilterToday implements AppointmentFilter {

    public static final AppointmentFilterToday INSTANCE = new AppointmentFilterToday();

    private AppointmentFilterToday() {

    }

    // Zu implementieren in Praktikum 6
    @Override
    public boolean test(Appointment appointment) {
        return true;
    }
}
