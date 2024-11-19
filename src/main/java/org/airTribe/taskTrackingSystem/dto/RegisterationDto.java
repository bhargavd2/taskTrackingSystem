package org.airTribe.taskTrackingSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterationDto {
    @NotEmpty(message = "must have firstName")
    private String firstName;
    @NotEmpty(message = "must have lastName")
    private String lastName;
    @NotEmpty(message = "must have email")
    private String email;
    @NotEmpty(message = "must have password")
    private String password;
}
