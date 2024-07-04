package com.majornick.notifications.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String preferredNotificationType;
}
