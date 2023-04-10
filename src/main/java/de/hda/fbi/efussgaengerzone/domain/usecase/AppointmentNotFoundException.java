package de.hda.fbi.efussgaengerzone.domain.usecase;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
