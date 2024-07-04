package com.majornick.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequestDTO {

    private String username;
    private String password;
}