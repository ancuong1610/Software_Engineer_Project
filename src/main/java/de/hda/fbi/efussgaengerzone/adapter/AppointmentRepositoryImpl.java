package de.hda.fbi.efussgaengerzone.adapter;

import de.hda.fbi.efussgaengerzone.domain.model.appointment.Appointment;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.AppointmentRepository;
import de.hda.fbi.efussgaengerzone.domain.usecase.AppointmentNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final Map<UUID, List<Appointment>> appointmentsByShop = new HashMap<>();

    @Override
    public Collection<Appointment> findForShopId(UUID shopId) {
        return appointmentsByShop.getOrDefault(shopId, new ArrayList<>());
    }

    @Override
    public Appointment save(UUID shopId, Appointment newAppointment) {
        appointmentsByShop.computeIfAbsent(shopId, k -> new ArrayList<>()).add(newAppointment);
        return newAppointment;
    }

    @Override
    public Appointment delete(UUID shopId, UUID appointmentId) {
        List<Appointment> shopAppointments = appointmentsByShop.getOrDefault(shopId, new ArrayList<>());

        Appointment appointment = shopAppointments.stream()
                .filter(a -> appointmentId.equals(a.id()))
                .findFirst()
                .orElseThrow(() -> new AppointmentNotFoundException(String.format("Appointment %s not found for shop %s", appointmentId, shopId)));

        shopAppointments.remove(appointment);
        return appointment;
    }


}
