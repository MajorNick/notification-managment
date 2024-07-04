package com.majornick.notifications.client.controller;

import com.majornick.notifications.client.dto.LoginFormDTO;
import com.majornick.notifications.client.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final  AuthenticationService authenticationService;
    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginFormDTO",new LoginFormDTO());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute LoginFormDTO loginFormDTO, Model model, HttpServletResponse response) {
        authenticationService.authenticate(loginFormDTO,response);
        return "home";
    }
}
