package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@SequenceGenerator(name = "customer_seq")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_seq")
    private Long id;

    private String fullName;

    private String email;
    private String mobilePhone;

    @OneToMany(mappedBy = "customer")
    private List<NotificationPreference> notificationPreferenceHistory;

    @OneToMany(mappedBy = "customer")
    private List<Notification> notifications;
}
