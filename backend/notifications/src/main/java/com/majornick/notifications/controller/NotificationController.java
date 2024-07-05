package com.majornick.notifications.controller;

import com.majornick.notifications.domain.enums.Status;
import com.majornick.notifications.dto.NotificationDTO;
import com.majornick.notifications.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications/{notificationId}")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API for getting and updating notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "Get a notification by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the notification",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @GetMapping
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable("notificationId") Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @Operation(summary = "Update the status of a notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification status updated"),
            @ApiResponse(responseCode = "400", description = "Invalid status input"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @PutMapping
    public ResponseEntity<?> updateNotification(@PathVariable("notificationId") Long id, @RequestParam("NotificationStatus") Status status) {
        notificationService.updateNotificationStatus(id, status);
        return ResponseEntity.ok("Notification updated.");
    }




}
