package com.majornick.notifications.controller;

import com.majornick.notifications.domain.enums.NotificationType;
import com.majornick.notifications.dto.AddressDTO;
import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public class CustomerController {
    private final CustomerService customerService;


    @GetMapping
    public ResponseEntity<?> getCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerDTO));
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(
            @RequestBody @Valid CustomerDTO customerDTO
    ) {
        customerService.updateCustomer(customerDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<?> getCustomerAddresses(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.findById(customerId).getAddresses());
    }

    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<?> addAddressToCustomer(@PathVariable("customerId") Long customerId,
                                                  @RequestBody AddressDTO addressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.addAddressToCustomer(customerId, addressDTO));
    }

    @PostMapping("/{customerId}/preferences")
    public ResponseEntity<?> createPreference(@PathVariable Long customerId, @RequestParam("NotificationType") NotificationType type) {
        customerService.createPreference(customerId, type);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
