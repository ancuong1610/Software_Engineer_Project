package de.hda.fbi.efussgaengerzone.views.mapper;

import de.hda.fbi.efussgaengerzone.domain.model.shop.OpeningHours;
import de.hda.fbi.efussgaengerzone.domain.model.shop.WeeklyOpeningHours;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

public class WeeklyOpeningHoursMapper {

    private static final String OPENING_HOURS_FIELD = "shop[opening-hours-%s-%s]";

    private WeeklyOpeningHoursMapper() {
    }

    public static WeeklyOpeningHours mapWeeklyOpeningHours(Map<String, String> requestParams) {
        return new WeeklyOpeningHours(
                mapDailyOpeningHours(DayOfWeek.MONDAY, requestParams),
                mapDailyOpeningHours(DayOfWeek.TUESDAY, requestParams),
                mapDailyOpeningHours(DayOfWeek.WEDNESDAY, requestParams),
                mapDailyOpeningHours(DayOfWeek.THURSDAY, requestParams),
                mapDailyOpeningHours(DayOfWeek.FRIDAY, requestParams),
                mapDailyOpeningHours(DayOfWeek.SATURDAY, requestParams)
        );
    }

    private static OpeningHours mapDailyOpeningHours(DayOfWeek weekday, Map<String, String> requestParams) {
        Boolean isOpen = "on".equals(requestParams.get(String.format(OPENING_HOURS_FIELD, weekday.name(), "open")));
        if (Boolean.FALSE.equals(isOpen)) {
            return null;
        }
        return new OpeningHours(
                LocalTime.parse(requestParams.get(String.format(OPENING_HOURS_FIELD, weekday.name(), "start"))),
                LocalTime.parse(requestParams.get(String.format(OPENING_HOURS_FIELD, weekday.name(), "end")))
        );
    }


}
