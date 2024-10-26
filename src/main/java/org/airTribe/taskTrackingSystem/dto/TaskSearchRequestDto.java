package org.airTribe.taskTrackingSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskSearchRequestDto {

    @NotNull
    private String searchfilter; // Could be 'status', 'title', 'description'
    @NotNull
    private String value; // The value to search against
}
