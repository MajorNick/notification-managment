package com.majornick.notifications.client.controller;

import com.majornick.notifications.client.service.NotificationClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final NotificationClientService clientService;
    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers",clientService.getAllCustomer());
        return "customers/index";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        // Fetch and add customer to model
        model.addAttribute("customer",clientService.getCustomer(id));
        return "customers/profile";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        clientService.deleteCustomer(id);
        return "redirect:/customers";
    }
}