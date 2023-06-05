package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.shop.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

// Coverage ist zu vervollständigen in Praktikum 9
@ExtendWith(MockitoExtension.class)
class ShopBrowsingTest {

    @Mock
    private ShopRepository repository;

    @InjectMocks
    private ShopBrowsing shopBrowsing;

    final OpeningHours nineToFive = new OpeningHours(LocalTime.of(7, 0), LocalTime.of(16, 0));
    final WeeklyOpeningHours usualWeeklyOpeningHours = new WeeklyOpeningHours(nineToFive, nineToFive, nineToFive, nineToFive, nineToFive, null);

    final private Shop coffeeShop = new Shop(
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
            "test@owner.de"
    );

    final private Shop cigarShop = new Shop(
            UUID.randomUUID(),
            "Cigar Shop",
            "Zigarren, Zigarillos und die gelegentliche Tabakpfeife für den feinen Herrn von morgen. " +
                    "Wir konzentrieren uns auf Cubanische, Deutsche und Französische Tabakwaren und bieten Ihnen " +
                    "und Ihren Mitbewohnern den klassischen Kneipengeruch aus den 70er Jahren. Bestellen Sie bei uns" +
                    "und Sie erhalten einen Gutschein über 20 EUR für den ersten Lungenvorsorgetermin dazu.",
            Set.of(VideoMessenger.SKYPE, VideoMessenger.ZOOM),
            usualWeeklyOpeningHours,
            Set.of(Tag.of("cigar"), Tag.of("cigarette"), Tag.of("yolo")),
            true,
            10,
            "test@owner.de"
    );

    final private Shop wineShop = new Shop(
            UUID.randomUUID(),
            "The Fine Wine Shop",
            "Emma war der Name meiner Großmutter. Sie war Gastgeberin durch und durch und ihr Name steht " +
                    "für mich für die Atmosphäre, die wir in dieser Weinbar schaffen wollen – ein Gefühl von" +
                    " „Zuhause“. Ein Platz zum Wohlfühlen und Spaß haben. Ein Ort, an dem man sich mit Freunden" +
                    " trifft, gute Unterhaltungen führt und dazu ein Glas Wein und etwas Leckeres genießt. Aber der " +
                    "Name steht noch für mehr, nämlich für ein „Danke“ an meine Familie, ohne die das EMMAS ein" +
                    " Traum geblieben wäre.",
            Set.of(VideoMessenger.ZOOM),
            usualWeeklyOpeningHours,
            Set.of(Tag.of("wine"), Tag.of("deluxe")),
            true,
            15,
            "test@owner.de"
    );

    final private Shop wineAndCoffee = new Shop(
            UUID.randomUUID(),
            "Wine and Coffee Shop",
            "Unser Name ist Programm. Wir bieten Ihnen Weine von kleinen, aber ausgezeichneten Wein-" +
                    " gütern, von welchen wir unsere Ware direkt beziehen. Der Umweg über den Zwischenhandel " +
                    "entfällt, so dass Spitzenweine zu einem günstigen Preis angeboten werden können.",
            Set.of(VideoMessenger.ZOOM),
            usualWeeklyOpeningHours,
            Set.of(Tag.of("deluxe"), Tag.of("yolo")),
            true,
            10,
            "test@owner.de"
    );

    final private Set<Shop> coffeeCigarAndWineShops = Set.of(coffeeShop, cigarShop, wineShop, wineAndCoffee);

    // zu implementieren in Praktikum 5
    @Nested
    class FindAll {

        @Test
        void whenRepositoryIsEmptyThenFindAllReturnsAnEmptyResult() {
// given
            given(repository.findAll()).willReturn(emptySet());

// when
            var shops = shopBrowsing.findAll();

// then
            assertThat(shops).isEmpty();
            verify(repository).findAll();
        }

        @Test
        void whenShopsExistsThenFindAllReturnsTheShops() {
        }

    }

    // zu implementieren in Praktikum 5
    @Nested
    class FindByQuery {

        @Test
        void whenRepositoryIsEmptyThenFindByqueryRetunrsAnEmptyResult() {
        }

        @Test
        void whenShopExistsAndFindByQueryIsCalledWithComplexQuerysThenItReturnsAnExpectedResult() {

        }
    }

}