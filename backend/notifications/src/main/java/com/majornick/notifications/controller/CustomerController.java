package com.majornick.notifications.controller;

import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping
    public ResponseEntity<?> getCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") Long customerId){
        return ResponseEntity.ok(customerService.findById(customerId));
    }
    @PostMapping
    public ResponseEntity<?> getCustomerById(@RequestBody @Valid CustomerDTO customerDTO){
        return ResponseEntity.ok(customerService.save(customerDTO));
    }
}
