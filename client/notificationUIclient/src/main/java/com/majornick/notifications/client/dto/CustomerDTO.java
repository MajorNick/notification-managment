package com.majornick.notifications.client.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String fullName;

    private String email;
    private String mobilePhone;
    private String preferredNotificationType;
    private List<AddressDTO> addresses;
}
