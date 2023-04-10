package de.hda.fbi.efussgaengerzone.views.controller;

import de.hda.fbi.efussgaengerzone.domain.usecase.OwnerRegistration;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class OwnerLoginController {

    public static final String COOKIE_NAME = "efzauth";

    private final OwnerRegistration ownerRegistration;

    public OwnerLoginController(OwnerRegistration ownerRegistration) {
        this.ownerRegistration = ownerRegistration;
    }

    @GetMapping(path = "/login")
    public ModelAndView showLogin() {
        return new ModelAndView("ownerLogin");
    }

    @GetMapping(path = "/logout")
    public ModelAndView showLogout(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0); // -> remove cookie
        response.addCookie(cookie);
        return new ModelAndView("logout");
    }


    @PostMapping(path = "/login")
    public ModelAndView login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            HttpServletResponse response) {
        if (ownerRegistration.login(username, password)) {
            response.addCookie(new Cookie(COOKIE_NAME, username));
            return new ModelAndView("redirect:/owner");
        }
        return new ModelAndView("ownerLogin", Map.of("failure", true));
    }
}