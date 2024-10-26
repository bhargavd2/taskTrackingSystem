package org.airTribe.taskTrackingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false, length = 255)
    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.LOW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "created_by", referencedColumnName = "userId")
    private User createdBy;
    @JsonIgnore
    private LocalDate CreatedDate;

    @JsonProperty("createdById")
    public Long getCreatedById() {
        return createdBy != null ? createdBy.getUserId() : null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "updated_by", referencedColumnName = "userId")
    private User updatedBy;
    @JsonIgnore
    private LocalDate UpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", referencedColumnName = "userId")
    private User assignedTo;

    @JsonProperty("assignedToId")
    public Long getAssignedToId() {
        return assignedTo != null ? assignedTo.getUserId() : null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private Project project;

    @JsonProperty("projectId")
    public Long getProjectId() {
        return project != null ? project.getProjectId() : null;
    }

}

