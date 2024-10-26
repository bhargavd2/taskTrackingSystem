package org.airTribe.taskTrackingSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.airTribe.taskTrackingSystem.annotate.FutureDate;
import org.airTribe.taskTrackingSystem.annotate.ValidPriority;
import org.airTribe.taskTrackingSystem.annotate.ValidStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskRequestDto {

    @NotEmpty(message = "must have title")
    private String title;
    @NotEmpty(message = "must have description")
    private String description;
    @NotEmpty(message = "must have dueDate")
    @FutureDate
    private LocalDate dueDate;
    @ValidPriority
    private String priority;
    private Long assignedToUserId;
    @NotEmpty(message = "must have projectId")
    private long projectId;

}
