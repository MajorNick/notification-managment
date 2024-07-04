package com.majornick.notifications.dto;

import com.majornick.notifications.domain.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    private AddressType addressType;
    private String addressValue;
}
