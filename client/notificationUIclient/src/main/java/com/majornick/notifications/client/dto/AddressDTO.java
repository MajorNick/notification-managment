package com.majornick.notifications.client.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AddressDTO {
    private Long id;
    private String addressType;
    private String addressValue;
}
