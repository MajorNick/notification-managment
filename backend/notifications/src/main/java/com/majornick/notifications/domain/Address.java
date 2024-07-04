package com.majornick.notifications.domain;

import com.majornick.notifications.domain.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "address_seq")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    private Long id;

    private AddressType addressType;
    private String addressValue;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
