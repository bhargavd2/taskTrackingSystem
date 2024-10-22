package org.airTribe.taskTrackingSystem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private String jwtToken;
    private long expiresIn;

}