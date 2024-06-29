package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.Status;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@SequenceGenerator(name = "notification_seq")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "notification_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Timestamp sendTime;


    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;

}
