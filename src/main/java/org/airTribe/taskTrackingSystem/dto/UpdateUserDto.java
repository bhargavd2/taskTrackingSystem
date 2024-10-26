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

    @NotNull(message = "must have firstName")
    private String firstName;
    @NotNull(message = "must have lastName")
    private String lastName;
}
