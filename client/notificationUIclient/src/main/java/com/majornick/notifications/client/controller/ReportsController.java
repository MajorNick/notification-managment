package com.majornick.notifications.client.controller;

import com.majornick.notifications.client.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsController {
    private final ReportsService reportsService;
    @GetMapping
    public String getStatistics(Model model) {
        model.addAttribute("notificationStatuses", reportsService.getNotificationStatusStatistics() );
        model.addAttribute("notificationPreferences", reportsService.getPreferedNotificationStatistics() );
        return "reports";
    }
}

