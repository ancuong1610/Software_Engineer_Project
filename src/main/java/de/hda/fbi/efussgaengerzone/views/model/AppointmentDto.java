package de.hda.fbi.efussgaengerzone.views.model;

import de.hda.fbi.efussgaengerzone.domain.model.appointment.Appointment;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record AppointmentDto(UUID id, String customerName, String chosenMessenger,
                             String videoMessengerContact, String date, String time, Duration duration) {

    public static AppointmentDto fromAppointment(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        return new AppointmentDto(
                appointment.id(),
                appointment.customerName(),
                appointment.chosenMessenger().label,
                appointment.videoMessengerContact(),
                appointment.dateTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                appointment.dateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                appointment.duration()
        );
    }

}
