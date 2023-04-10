package de.hda.fbi.efussgaengerzone.views.model;

import de.hda.fbi.efussgaengerzone.domain.model.shop.WeeklyOpeningHours;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// List that contains all available weekdays in the shop system and an arbitrary content
// belong to each weekday. Example questions that can be answered using this class:
// * on which weekdays is the shop closed?
// * which time slots are available for booking per weekday?
public class WeekdayListDto<T> extends ArrayList<WeekdayListDto.Weekday<T>> {

    private WeekdayListDto(T monday, T tuesday, T wednesday, T thursday, T friday, T saturday) {
        add(new Weekday<>(DayOfWeek.MONDAY, monday));
        add(new Weekday<>(DayOfWeek.TUESDAY, tuesday));
        add(new Weekday<>(DayOfWeek.WEDNESDAY, wednesday));
        add(new Weekday<>(DayOfWeek.THURSDAY, thursday));
        add(new Weekday<>(DayOfWeek.FRIDAY, friday));
        add(new Weekday<>(DayOfWeek.SATURDAY, saturday));
    }

    public static WeekdayListDto<OpeningHoursDto> fromWeeklyOpeningHours(WeeklyOpeningHours openingHours) {
        return new WeekdayListDto<>(
                OpeningHoursDto.fromOpeningHours(openingHours.monday()),
                OpeningHoursDto.fromOpeningHours(openingHours.tuesday()),
                OpeningHoursDto.fromOpeningHours(openingHours.saturday()),
                OpeningHoursDto.fromOpeningHours(openingHours.wednesday()),
                OpeningHoursDto.fromOpeningHours(openingHours.thursday()),
                OpeningHoursDto.fromOpeningHours(openingHours.friday())
        );
    }

    public static WeekdayListDto<List<LocalTime>> fromBookingTimes(Map<DayOfWeek, List<LocalTime>> availableTimes) {
        return new WeekdayListDto<>(
                availableTimes.get(DayOfWeek.MONDAY),
                availableTimes.get(DayOfWeek.TUESDAY),
                availableTimes.get(DayOfWeek.WEDNESDAY),
                availableTimes.get(DayOfWeek.THURSDAY),
                availableTimes.get(DayOfWeek.FRIDAY),
                availableTimes.get(DayOfWeek.SATURDAY)
        );
    }

    public static WeekdayListDto<OpeningHoursDto> empty() {
        return new WeekdayListDto<>(null, null, null, null, null, null);
    }

    record Weekday<T>(
            DayOfWeek day, T content
    ) {
        // This function is used in Mustache templates
        public String dayDisplayName() {
            return day.getDisplayName(TextStyle.FULL, Locale.GERMAN);
        }
    }

}
