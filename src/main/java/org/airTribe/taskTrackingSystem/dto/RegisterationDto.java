package org.airTribe.taskTrackingSystem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
