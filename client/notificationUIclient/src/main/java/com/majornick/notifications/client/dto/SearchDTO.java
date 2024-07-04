package com.majornick.notifications.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String preferredNotificationType;
}
