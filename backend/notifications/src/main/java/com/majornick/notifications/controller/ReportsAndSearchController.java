package com.majornick.notifications.controller;

import com.majornick.notifications.dto.SearchDTO;
import com.majornick.notifications.service.ReportsAndSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequiredArgsConstructor
@Tag(name = "Reports and Search", description = "API for generating reports and searching customers")
public class ReportsAndSearchController {
    private final ReportsAndSearchService reportsAndSearchService;

    @Operation(summary = "Get report on notification statuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved status report",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)) })
    })
    @GetMapping("/api/reports/statuses")
    public ResponseEntity<?> getStatusesOfNotifications() {
        return ResponseEntity.ok(reportsAndSearchService.getNotificationStatusReport());
    }

    @Operation(summary = "Search for customers by fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved search results",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) })
    })
    @PostMapping("/api/customers/search")
    public ResponseEntity<?> searchCustomers(@RequestBody SearchDTO searchDTO) {
        return ResponseEntity.ok(reportsAndSearchService.searchCustomers(searchDTO));
    }
}