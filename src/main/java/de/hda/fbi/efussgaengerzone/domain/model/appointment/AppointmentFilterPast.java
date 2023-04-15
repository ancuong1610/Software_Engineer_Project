package de.hda.fbi.efussgaengerzone.domain.model.appointment;

public class AppointmentFilterPast implements AppointmentFilter {

    public static final AppointmentFilterPast INSTANCE = new AppointmentFilterPast();

    private AppointmentFilterPast() {
    }

    // Zu implementieren in Praktikum 6
    @Override
    public boolean test(Appointment appointment) {
        return true;
    }
}
