package com.majornick.notifications.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    @NotNull
    private String fullName;
    @Email
    private String email;
    private String mobilePhone;
}
