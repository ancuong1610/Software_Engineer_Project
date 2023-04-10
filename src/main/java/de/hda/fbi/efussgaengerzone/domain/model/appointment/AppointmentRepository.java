package de.hda.fbi.efussgaengerzone.domain.model.appointment;

import java.util.Collection;
import java.util.UUID;

public interface AppointmentRepository {
    Collection<Appointment> findForShopId(UUID shopId);

    Appointment save(UUID shopId, Appointment newAppointment);

    Appointment delete(UUID shopId, UUID appointmentId);
}
