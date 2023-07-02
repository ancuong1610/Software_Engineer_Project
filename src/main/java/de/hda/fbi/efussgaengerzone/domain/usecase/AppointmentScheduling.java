package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.appointment.Appointment;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.AppointmentFilter;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.AppointmentRepository;
import de.hda.fbi.efussgaengerzone.domain.model.shop.OpeningHours;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.ShopRepository;
import de.hda.fbi.efussgaengerzone.domain.model.shop.WeeklyOpeningHours;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;

@Service
public class AppointmentScheduling {

    private final AppointmentRepository appointmentRepository;
    private final ShopRepository shopRepository;

    public AppointmentScheduling(AppointmentRepository appointmentRepository, ShopRepository shopRepository) {
        this.appointmentRepository = appointmentRepository;
        this.shopRepository = shopRepository;
    }

    // zu implementieren in Praktikum 8
    public void makeAppointment(UUID shopid, Appointment appointment) {
        appointmentRepository.save(shopid, appointment);
    }

    // zu implementieren in Praktikum 8
    public void deleteAppointment(UUID shopId, UUID appointmentId) {
        appointmentRepository.delete(shopId, appointmentId);
    }

    // zu implementieren in Praktikum 6
    public Optional<Appointment> findNextAppointment(UUID shopId) {
        return appointmentRepository.findForShopId(shopId)
                .stream().filter(appointment -> appointment.dateTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Appointment::dateTime))
                .findFirst();
    }

    // zu implementieren in Praktikum 6
    public Collection<Appointment> searchAppointments(UUID shopId, Set<AppointmentFilter> filters) {
        Predicate<Appointment> combinedFilter = filters.stream()
                .map(filter -> (Predicate<Appointment>) filter)
                .reduce(Predicate::or).orElse(x -> false);

        return appointmentRepository.findForShopId(shopId)
                .stream()
                .filter(filters.isEmpty() ? a -> true : combinedFilter)
                .toList();
    }

    // zu implementieren in Praktikum 8
    public List<LocalTime> availableDatesOnDay(UUID shopid, DayOfWeek dayOfWeek) throws ShopNotFoundException {
        Shop shop = shopRepository.findById(shopid)
                .orElseThrow(() -> new ShopNotFoundException(shopid));

        OpeningHours openingHours = getOpeningHoursForDay(dayOfWeek, shop.weeklyOpeningHours());
        if (openingHours == null) {
            // shop is not open on the selected weekday. no opening hours available.
            return List.of();
        }

        Integer minsPerCustomer = shop.minsPerCustomer();
        List<LocalTime> allDates = new ArrayList<>();
        for (LocalTime time = openingHours.openingTime();
             time.compareTo(openingHours.closingTime()) < 0;
             time = time.plus(minsPerCustomer, ChronoUnit.MINUTES)) {
            allDates.add(time);
        }

        Collection<Appointment> appointments = appointmentRepository.findForShopId(shopid);

        // remove all dates which have been booked already
        return allDates.stream().filter(date -> appointments.stream().noneMatch(a -> a.dateTime().toLocalTime().equals(date)))
                .toList();
    }

    // zu implementieren in Praktikum 8
    private OpeningHours getOpeningHoursForDay(DayOfWeek dayOfWeek, WeeklyOpeningHours weeklyOpeningHours) {
        switch (dayOfWeek) {
            case MONDAY:
                return weeklyOpeningHours.monday();
            case TUESDAY:
                return weeklyOpeningHours.tuesday();
            case WEDNESDAY:
                return weeklyOpeningHours.wednesday();
            case THURSDAY:
                return weeklyOpeningHours.thursday();
            case FRIDAY:
                return weeklyOpeningHours.friday();
            case SATURDAY:
                return weeklyOpeningHours.saturday();
            default:
                throw new IllegalArgumentException();
        }
    }
}