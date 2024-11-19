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
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "userId")
    @JsonIgnore
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
}
