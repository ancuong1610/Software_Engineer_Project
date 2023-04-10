package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.appointment.Appointment;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.AppointmentFilter;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AppointmentScheduling {

    public void makeAppointment(UUID shopid, Appointment appointment) {

    }

    public void deleteAppointment(UUID shopId, UUID appointmentId) {

    }

    public Optional<Appointment> findNextAppointment(UUID shopId) {
        return Optional.empty();
    }

    public Collection<Appointment> searchAppointments(UUID shopId, Set<AppointmentFilter> filters) {
        return List.of();
    }

    public List<LocalTime> availableDatesOnDay(UUID shopid, DayOfWeek dayOfWeek) throws ShopNotFoundException {
        return List.of();
    }
}
