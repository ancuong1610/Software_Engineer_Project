package de.hda.fbi.efussgaengerzone.domain.model.appointment;

public class AppointmentFilterFuture implements AppointmentFilter {

    public static final AppointmentFilterFuture INSTANCE = new AppointmentFilterFuture();

    private AppointmentFilterFuture() {

    }

    // Zu implementieren in Praktikum 6
    @Override
    public boolean test(Appointment appointment) {
        return true;
    }
}
