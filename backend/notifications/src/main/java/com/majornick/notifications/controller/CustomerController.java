package com.majornick.notifications.controller;

import com.majornick.notifications.domain.enums.NotificationType;
import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.dto.NotificationDTO;
import com.majornick.notifications.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "API for handling customers,assigning notifications and  it's preferences.")
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) })
    })
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @Operation(summary = "Get a customer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customerId") Long customerId) {

        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody  List<@Valid CustomerDTO> customerDTOList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerDTOList));
    }
    @Operation(summary = "Update an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping
    public ResponseEntity<?> updateCustomer(
            @RequestBody @Valid CustomerDTO customerDTO
    ) {
        customerService.updateCustomer(customerDTO);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Delete a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Switch customer notification preference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preference switched successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PostMapping("/{customerId}/preferences")
    public ResponseEntity<?> switchPreference(@PathVariable Long customerId, @RequestParam("NotificationType") NotificationType type) {
        customerService.switchPreference(customerId, type);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Assign a notification to a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification assigned successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PostMapping("/{customerId}/notifications")
    public ResponseEntity<?> assignNotification(@PathVariable Long customerId, NotificationDTO notificationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.assignNotificationToCustomer(customerId, notificationDTO));
    }


}
