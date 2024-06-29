package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.NotificationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class NotificationPreference {
    @Id
    @GeneratedValue
    private Long id;


    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;
}
