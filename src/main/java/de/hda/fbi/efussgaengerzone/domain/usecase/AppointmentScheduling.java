package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.appointment.Appointment;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.AppointmentFilter;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.AppointmentRepository;
import de.hda.fbi.efussgaengerzone.domain.model.shop.OpeningHours;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

@Service
public class AppointmentScheduling {
    final ShopOrganization shopOrganization;
    final AppointmentRepository appointmentRepository;

    public AppointmentScheduling(ShopOrganization shopOrganization, AppointmentRepository appointmentRepository) {
        this.shopOrganization = shopOrganization;
        this.appointmentRepository = appointmentRepository;
    }

    public void makeAppointment(UUID shopid, Appointment appointment) {
        appointmentRepository.save(shopid, appointment);
    }

    public void deleteAppointment(UUID shopId, UUID appointmentId) {
        if (appointmentRepository.findForShopId(shopId).stream().anyMatch(appointment -> appointment.id().equals(appointmentId)))
            appointmentRepository.delete(shopId, appointmentId);
    }

    public Optional<Appointment> findNextAppointment(UUID shopId) {
        var appointments = appointmentRepository.findForShopId(shopId);
        return appointments.stream().filter(appointment -> appointment.dateTime().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()))).min(Comparator.comparing(Appointment::dateTime));
        //return Optional.empty();
    }
    public Collection<Appointment> searchAppointments(UUID shopId, Set<AppointmentFilter> filters) {
        System.out.println("Searching appointments for shop " + shopId + " " + shopOrganization.shopRepository.findById(shopId).get().name());
        //for (Appointment a:appointmentRepository.findForShopId(shopId)) {
        //    System.out.println(a);
        //}
        //for (AppointmentFilter filter:filters
        //     ) {
        //    System.out.println(filter);
        //}
        return appointmentRepository.findForShopId(shopId).stream().filter(appointment -> filters.stream().allMatch(appointmentFilter -> appointmentFilter.test(appointment))).toList();
    }
    public List<LocalTime> availableDatesOnDay(UUID shopid, DayOfWeek dayOfWeek) throws ShopNotFoundException {
        var s = shopOrganization.shopRepository.findById(shopid).orElseThrow(() -> new ShopNotFoundException(shopid));
        //System.out.println("Finding available dates for shop " + s.name() + " on " + dayOfWeek);
        OpeningHours openingHours;
        // get corresponding opening hours from shop by dayOfWeek
        switch (dayOfWeek) {
            case MONDAY -> {
                openingHours = s.weeklyOpeningHours().monday();
                break;
            }
            case TUESDAY -> {
                openingHours = s.weeklyOpeningHours().tuesday();
                break;
            }
            case WEDNESDAY -> {
                openingHours = s.weeklyOpeningHours().wednesday();
                break;
            }
            case THURSDAY -> {
                openingHours = s.weeklyOpeningHours().thursday();
                break;
            }
            case FRIDAY -> {
                openingHours = s.weeklyOpeningHours().friday();
                break;
            }
            case SATURDAY -> {
                openingHours = s.weeklyOpeningHours().saturday();
                break;
            }
            case SUNDAY -> {
                openingHours = null;
                break;
            }
            default -> {
                throw new IllegalStateException("Unexpected value: " + dayOfWeek);
            }
        }
        if (openingHours == null) return List.of();
        // get all appointments for this shop
        List<LocalTime> output = new ArrayList<>();
        LocalTime timeCounter = openingHours.openingTime();
        while (timeCounter.isBefore(openingHours.closingTime())) {
            output.add(timeCounter);
            timeCounter = timeCounter.plusMinutes(s.minsPerCustomer());
        }
        for (Appointment a : appointmentRepository.findForShopId(shopid)) {
            if (a.dateTime().getDayOfWeek() == dayOfWeek) {
                output.remove(a.dateTime().toLocalTime());
            }
        }
        /*+
        for (int i = openingHours.openingTime().getHour(); i <= openingHours.closingTime().getHour(); i++) {
            output.add(LocalTime.of(i, 0));
        }
         */
        return output;
    }
}