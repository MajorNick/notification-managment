package com.majornick.notifications.dto;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequestDTO {

    private String username;
    private String password;
}