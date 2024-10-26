package org.airTribe.taskTrackingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_project")
public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @JsonProperty("userId")
    public Long getCreatedById() {
        return user != null ? user.getUserId() : null;
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
