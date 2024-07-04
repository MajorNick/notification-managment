package com.majornick.notifications.dto;

import com.majornick.notifications.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDTO {
    private Long id;
    private Status status;
    private String content;
    private LocalDateTime sendTime;
}
