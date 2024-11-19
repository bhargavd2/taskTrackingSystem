package org.airTribe.taskTrackingSystem.repository;

import org.airTribe.taskTrackingSystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
