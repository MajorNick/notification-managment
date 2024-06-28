package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.NotificationType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@SequenceGenerator(name = "customer_seq")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_seq")
    private Long id;

    private String fullName;

    private String email;
    private String mobilePhone;

    @Enumerated(EnumType.STRING)
    private NotificationType prefferedNotificationType;

    @OneToMany(mappedBy = "customer")
    private List<Notification> notifications;
}
