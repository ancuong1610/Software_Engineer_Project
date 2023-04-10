package de.hda.fbi.efussgaengerzone.views.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static de.hda.fbi.efussgaengerzone.views.controller.OwnerLoginController.COOKIE_NAME;

@Configuration
public class AuthConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());
    }
}

class AuthInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Adds an object to the ModelAndView with information about whether
        // the user is authenticated or not.
        // This flag can be evaluated by the mustache templates to show a login or logout button
        if (modelAndView != null) {
            boolean isAuthenticated = Arrays.stream(request.getCookies()).anyMatch(c -> COOKIE_NAME.equals(c.getName()));
            modelAndView.addObject("authenticated", isAuthenticated);
        }
    }
}