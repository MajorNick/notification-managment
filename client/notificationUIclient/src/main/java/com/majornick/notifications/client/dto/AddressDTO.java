package com.majornick.notifications.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    private String addressType;
    private String addressValue;
    private LocalDateTime updateDate;
}
