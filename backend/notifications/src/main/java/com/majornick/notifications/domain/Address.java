package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.AddressType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private AddressType addressType;
    private String addressValue;
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;
}
