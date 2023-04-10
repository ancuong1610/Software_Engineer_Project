package de.hda.fbi.efussgaengerzone.views.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(Exception ex) {
        LOGGER.error("Unhandled exception occurred", ex);
        return new ModelAndView("redirect:/error");
    }

}
