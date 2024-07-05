package com.majornick.notifications.controller;

import com.majornick.notifications.dto.AddressDTO;
import com.majornick.notifications.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
@RequiredArgsConstructor
@Tag(name = "Addresses", description = "API for getting and posting customer addresses")
public class AddressController {
    private final AddressService addressService;
    @Operation(summary = "Get all addresses for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer's addresses",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getCustomerAddresses(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(addressService.getCustomerAddresses(customerId));
    }
    @Operation(summary = "Add a new address to a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address added successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PostMapping
    public ResponseEntity<?> addAddressToCustomer(@PathVariable("customerId") Long customerId,
                                                  @RequestBody AddressDTO addressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addAddressToCustomer(customerId, addressDTO));
    }
}
