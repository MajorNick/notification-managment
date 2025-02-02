package com.majornick.notifications.client.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class CustomerDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String preferredNotificationType;
    private List<AddressDTO> addresses;
}
