package com.majornick.notifications.mapper;

import com.majornick.notifications.domain.Notification;
import com.majornick.notifications.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotificationMapper {
    private final ModelMapper modelMapper;

    public Notification toNotification(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }

    public NotificationDTO toDto(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }
}
