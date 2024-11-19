package org.airTribe.taskTrackingSystem.repository;

import org.airTribe.taskTrackingSystem.entity.Status;
import org.airTribe.taskTrackingSystem.entity.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCreatedByUserId(Long userId);
    List<Task> findByAssignedToUserId(Long userId);
    List<Task> findByProjectProjectIdAndCreatedByUserId(Long projectId,Long userId);
    List<Task> findByProjectProjectIdAndAssignedToUserId(Long projectId,Long userId);
    List<Task> findByStatusAndCreatedByUserId(Status status, Long userId);
    List<Task> findByStatusAndAssignedToUserId(Status status, Long userId);
    List<Task> findByTitleContainingIgnoreCaseAndCreatedByUserId(String title, Long userId);
    List<Task> findByDescriptionContainingIgnoreCaseAndCreatedByUserId(String description, Long userId);
    List<Task> findByTitleContainingIgnoreCaseAndAssignedToUserId(String title, Long userId);
    List<Task> findByDescriptionContainingIgnoreCaseAndAssignedToUserId(String description, Long userId);
}
