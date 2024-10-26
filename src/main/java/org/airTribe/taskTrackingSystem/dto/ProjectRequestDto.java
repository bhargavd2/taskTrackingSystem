package org.airTribe.taskTrackingSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectRequestDto {
    @NotEmpty(message = "must have name")
    private String name;
    @NotEmpty(message = "must have description")
    private String description;
}
