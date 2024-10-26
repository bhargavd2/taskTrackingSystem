package org.airTribe.taskTrackingSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskSearchRequestDto {

    @NotEmpty
    private String searchfilter; // Could be 'status', 'title', 'description'
    @NotEmpty
    private String value; // The value to search against
}
