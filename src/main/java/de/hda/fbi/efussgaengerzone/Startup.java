package de.hda.fbi.efussgaengerzone;

import de.hda.fbi.efussgaengerzone.domain.model.appointment.Appointment;
import de.hda.fbi.efussgaengerzone.domain.model.appointment.AppointmentRepository;
import de.hda.fbi.efussgaengerzone.domain.model.owner.Owner;
import de.hda.fbi.efussgaengerzone.domain.model.owner.OwnerRepository;
import de.hda.fbi.efussgaengerzone.domain.model.shop.OpeningHours;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Shop;
import de.hda.fbi.efussgaengerzone.domain.model.shop.ShopRepository;
import de.hda.fbi.efussgaengerzone.domain.model.shop.Tag;
import de.hda.fbi.efussgaengerzone.domain.model.shop.VideoMessenger;
import de.hda.fbi.efussgaengerzone.domain.model.shop.WeeklyOpeningHours;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// Klasse "Startup" wird im Praktikum 4 geändert!
@Component
@Profile("debug")
public class Startup {

    private static final String CUSTOMER_1 = "Wiebke Wodka";
    private static final String CUSTOMER_2 = "Wilhelm Whiskey";
    private static final String CUSTOMER_3 = "Werner Wein";
    private static final String CUSTOMER_4 = "Walter White";
    private static final String CUSTOMER_5 = "Wolfgang Waldmeisterbowle";

    private final ShopRepository shopRepository;
    private final OwnerRepository ownerRepository;
    private final AppointmentRepository appointmentRepository;

    public Startup(ShopRepository shopRepository, OwnerRepository ownerRepository, AppointmentRepository appointmentRepository) {
        this.shopRepository = shopRepository;
        this.ownerRepository = ownerRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("*** init ***");
        setShopDebugData(createCoffeeShopOwner());
    }

    private Owner createCoffeeShopOwner() {
        // for testing shop with appointment data
        final var coffeeShopOwner = new Owner(
                "Mr. Coffee",
                "owner@coffee.shop",
                "123"
        );

        ownerRepository.save(coffeeShopOwner);
        return coffeeShopOwner;
    }

    private void setShopDebugData(Owner coffeeShopOwner) {
        final var nineToFive = new OpeningHours(LocalTime.of(7, 0), LocalTime.of(16, 0));
        final var usualWeeklyOpeningHours = new WeeklyOpeningHours(nineToFive, nineToFive, nineToFive, nineToFive, nineToFive, null);

        final var coffeeShop = new Shop(
                UUID.randomUUID(),
                "Student Coffee Shop",
                "Lust auf einen Original Mensa Kaffee? Dann bist du in unserem Laden genau richtig! Wir, Felix" +
                        " und Dieter, bringen dir die alte Studentenzeit nach Hause und beliefern dich mit Originalen" +
                        " Kaffesorten aus verschiedenen Mensen Deutschlands. Mit dabei haben wir zum Beispiel den leckeren" +
                        "aus Darmstadt, die liebevoll genannte 'Dunkle Plörre' aus Aachen und den starken aus Gießen." +
                        "Bestell bei uns und dir wird bald genauso schlecht wie früher vom Mensa Essen.",
                Set.of(VideoMessenger.WHATSAPP, VideoMessenger.SKYPE),
                usualWeeklyOpeningHours,
                Set.of(Tag.of("coffee"), Tag.of("cheap")),
                true,
                15,
                coffeeShopOwner.email()
        );

        shopRepository.clear();
        shopRepository.save(coffeeShop);


        final Collection<Appointment> coffeeShopAppointments = List.of(
                new Appointment(UUID.randomUUID(), CUSTOMER_1, VideoMessenger.SKYPE, "wwod", LocalDateTime.now().minus(7, ChronoUnit.DAYS), Duration.ofMinutes(30)),
                new Appointment(UUID.randomUUID(), CUSTOMER_2, VideoMessenger.ZOOM, "MEETING-1", LocalDateTime.now().minus(2, ChronoUnit.DAYS), Duration.ofMinutes(15)),
                new Appointment(UUID.randomUUID(), CUSTOMER_1, VideoMessenger.SKYPE, "wwod", LocalDateTime.now(), Duration.ofMinutes(15)),
                new Appointment(UUID.randomUUID(), CUSTOMER_3, VideoMessenger.ZOOM, "w.wein", LocalDateTime.now().plus(3, ChronoUnit.HOURS), Duration.ofMinutes(15)),
                new Appointment(UUID.randomUUID(), CUSTOMER_4, VideoMessenger.TELEGRAM, "H.Berg", LocalDateTime.now().plus(2, ChronoUnit.DAYS), Duration.ofMinutes(30)),
                new Appointment(UUID.randomUUID(), CUSTOMER_1, VideoMessenger.SKYPE, "wwod", LocalDateTime.now().plus(7, ChronoUnit.DAYS), Duration.ofMinutes(30)),
                new Appointment(UUID.randomUUID(), CUSTOMER_5, VideoMessenger.SKYPE, "wwb", LocalDateTime.now().plus(7, ChronoUnit.DAYS), Duration.ofMinutes(30))
        );
        coffeeShopAppointments.forEach(a -> appointmentRepository.save(coffeeShop.id(), a));
    }
}
