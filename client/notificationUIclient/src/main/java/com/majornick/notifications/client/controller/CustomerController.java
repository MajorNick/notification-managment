package com.majornick.notifications.client.controller;

import com.majornick.notifications.client.dto.AddressDTO;
import com.majornick.notifications.client.dto.CustomerDTO;
import com.majornick.notifications.client.dto.SearchDTO;
import com.majornick.notifications.client.service.NotificationClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final NotificationClientService clientService;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", clientService.getAllCustomer());
        model.addAttribute("searchDTO", new SearchDTO());
        return "customers/index";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", clientService.getCustomer(id));
        return "customers/profile";
    }

    @PostMapping("/createForm")
    public String goToCustomerForm(Model model) {
        CustomerDTO customerDTO = new CustomerDTO();
        AddressDTO addressDTO = new AddressDTO();
        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDTO);
        customerDTO.setAddresses(addresses);
        model.addAttribute("customerDTO", customerDTO);
        return "customers/create";
    }

    @GetMapping("/search")
    public String searchCustomer(@ModelAttribute SearchDTO searchDTO, Model model) {

        model.addAttribute("customers", clientService.searchCustomers(searchDTO));
        return "customers/index";
    }

    @PostMapping
    public String createCustomer(@ModelAttribute CustomerDTO customerDTO) {
        clientService.saveCustomer(customerDTO);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        clientService.deleteCustomer(id);
        return "redirect:/customers";
    }
}