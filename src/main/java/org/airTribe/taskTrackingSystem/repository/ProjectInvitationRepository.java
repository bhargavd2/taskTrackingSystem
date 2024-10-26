package org.airTribe.taskTrackingSystem.repository;

import org.airTribe.taskTrackingSystem.entity.ProjectInvitation;
import org.airTribe.taskTrackingSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectInvitationRepository extends JpaRepository<ProjectInvitation, Long> {


    List<ProjectInvitation> findByInvitationTo(User invitationTo);

    List<ProjectInvitation> findByCreatedBy(User createdBy);

}
