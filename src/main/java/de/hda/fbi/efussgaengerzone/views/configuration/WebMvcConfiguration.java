package de.hda.fbi.efussgaengerzone.views.configuration;

import de.hda.fbi.efussgaengerzone.views.model.AppointmentFilterTypeDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Locale;

/**
 * This configuration adds a converter for the {@link AppointmentFilterTypeDto} class. The class is used in
 * {@link de.hda.fbi.efussgaengerzone.views.controller.OwnerController#ownerAppointmentsView(HttpServletRequest, List)}
 * to filter appointments by their date. As enums should be uppercase by convention but the REST API should also support
 * lowercase values, the {@link AppointmentFilterTypeConverter} is added to the spring config to convert
 * {@link AppointmentFilterTypeDto} values to uppercase.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AppointmentFilterTypeConverter());
    }

    static class AppointmentFilterTypeConverter implements Converter<String, AppointmentFilterTypeDto> {
        @Override
        public AppointmentFilterTypeDto convert(String source) {
            return AppointmentFilterTypeDto.valueOf(source.toUpperCase(Locale.ENGLISH));
        }
    }
}
