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
@Table(name = "project_invitation")
public class ProjectInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonProperty("projectId")
    public Long getProjectId() {
        return project != null ? project.getProjectId() : null;
    }

    @Column(nullable = false, unique = true)
    private String inviteCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "invitation_to", referencedColumnName = "userId")
    private User invitationTo;

    @JsonProperty("invitationTo")
    public Long getInvitationTo() {
        return invitationTo != null ? invitationTo.getUserId() : null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "created_by", referencedColumnName = "userId")
    private User createdBy;

    @JsonProperty("createdById")
    public Long getCreatedById() {
        return createdBy != null ? createdBy.getUserId() : null;
    }
    @JsonIgnore
    private LocalDate CreatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "updated_by", referencedColumnName = "userId")
    private User updatedBy;

    @JsonIgnore
    private LocalDate UpdateDate;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.PENDING;
}

