package com.majornick.notifications.service;

import com.majornick.notifications.domain.Notification;
import com.majornick.notifications.domain.enums.Status;
import com.majornick.notifications.dto.NotificationDTO;
import com.majornick.notifications.exception.NotificationNotFoundException;
import com.majornick.notifications.mapper.NotificationMapper;
import com.majornick.notifications.repository.NotificationRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;

    public NotificationDTO getNotificationById(Long id) {
        Notification notif = notificationRepo.findById(id).orElseThrow(
                () -> new NotificationNotFoundException(String.format("notification  with the id: %d not Found", id))
        );
        return notificationMapper.toDto(notif);
    }

    @Transactional
    public void updateNotificationStatus(Long id, Status status) {
        Notification notif = notificationRepo.findById(id).orElseThrow(
                () -> new NotificationNotFoundException(String.format("notification  with the id: %d not Found", id))
        );
        notif.setStatus(status);
    }

    public Map<String,Long> getNotificationStatusReport(){
        var list = notificationRepo.countNotificationsByStatus();
        Map<String, Long> countMap = new HashMap<>();
        list.forEach(arr -> {
            String status = (arr[0]).toString();
            Long count = ((Number) arr[1]).longValue();
            countMap.put(status, count);
        });
        return countMap;
    }


}
