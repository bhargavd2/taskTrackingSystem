package org.airTribe.taskTrackingSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
