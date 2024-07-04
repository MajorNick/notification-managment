package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "notification_seq")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String content;
    private LocalDateTime sendTime;


    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;

}
