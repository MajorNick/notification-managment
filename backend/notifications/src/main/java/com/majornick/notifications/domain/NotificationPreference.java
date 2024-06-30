package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NotificationPreference {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @CreatedDate
    private LocalDateTime updateTime;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;
}
