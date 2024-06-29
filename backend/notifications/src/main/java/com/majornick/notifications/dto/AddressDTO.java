package com.majornick.notifications.dto;

import com.majornick.notifications.domain.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    private AddressType addressType;
    private String addressValue;
    private LocalDateTime updateDate;
}
