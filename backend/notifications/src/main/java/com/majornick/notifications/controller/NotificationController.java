package com.majornick.notifications.controller;

import com.majornick.notifications.domain.enums.Status;
import com.majornick.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{notificationId}")
    public ResponseEntity<?> getNotificationById(@PathVariable("notificationId") Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<?> getNotificationById(@PathVariable("notificationId") Long id, @RequestParam("NotificationStatus") Status status) {
        notificationService.updateNotificationStatus(id, status);
        return ResponseEntity.ok("Notification updated.");
    }

    @GetMapping("/reports/statuses")
    public ResponseEntity<?> getStatusesReports() {
        return ResponseEntity.ok(notificationService.getNotificationStatusReport());
    }


}
