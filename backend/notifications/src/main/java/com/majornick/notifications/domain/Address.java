package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private AddressType addressType;
    private String addressValue;
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
