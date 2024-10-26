package org.airTribe.taskTrackingSystem.repository;


import org.airTribe.taskTrackingSystem.entity.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    List<UserProject> findByProject_ProjectId(Long projectId);
}