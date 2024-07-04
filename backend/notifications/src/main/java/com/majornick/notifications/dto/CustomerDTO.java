package com.majornick.notifications.dto;

import com.majornick.notifications.domain.enums.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    @NotNull
    private String fullName;
    @Email
    private String email;
    private String phoneNumber;

    private NotificationType preferredNotificationType;
    private List<AddressDTO> addresses;
}
