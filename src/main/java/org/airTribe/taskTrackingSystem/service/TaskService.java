package org.airTribe.taskTrackingSystem.service;

import org.airTribe.taskTrackingSystem.entity.Status;
import org.airTribe.taskTrackingSystem.entity.Task;
import org.airTribe.taskTrackingSystem.exception.TaskNotFoundException;
import org.airTribe.taskTrackingSystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository _taskRepository;

    public Task getTaskById(long taskId)
    {
        Optional<Task> task = _taskRepository.findById(taskId);
        if(task.isEmpty()) throw new TaskNotFoundException("task not found");
        return task.get();
    }

    public List<Task> getAllTasksByUserID(Long userId)
    {
        return _taskRepository.findByCreatedByUserId(userId);
    }

    public List<Task> getAllTasksForUserID(Long userId){
        return _taskRepository.findByAssignedToUserId(userId);
    }

    public List<Task> getAllTasksByProjectIdByUserID(Long projectId,long userId)
    {
        return _taskRepository.findByProjectProjectIdAndCreatedByUserId(projectId,userId);
    }

    public List<Task> getAllTasksByProjectIdForUserID(Long projectId,long userId)
    {
        return _taskRepository.findByProjectProjectIdAndAssignedToUserId(projectId,userId);
    }

    public List<Task> findByStatusByUserId(Status status,long userId)
    {
        List<Task> tasks = _taskRepository.findByStatusAndAssignedToUserId(status,userId);
        tasks.addAll(_taskRepository.findByStatusAndCreatedByUserId(status,userId));
        return tasks;
    }

    public List<Task> findByTitleByUserId(String value, long userId)
    {
        List<Task> tasks = _taskRepository.findByTitleContainingIgnoreCaseAndCreatedByUserId(value,userId);
        tasks.addAll(_taskRepository.findByTitleContainingIgnoreCaseAndAssignedToUserId(value,userId));
        return tasks;
    }

    public List<Task> findByDescriptionByUserId(String value, long userId)
    {
        List<Task> tasks = _taskRepository.findByDescriptionContainingIgnoreCaseAndCreatedByUserId(value,userId);
        tasks.addAll(_taskRepository.findByDescriptionContainingIgnoreCaseAndAssignedToUserId(value,userId));
        return tasks;
    }

    public Task saveTask(Task task)
    {
        return _taskRepository.save(task);
    }

    public void deleteTaskById(Long taskId)
    {
        _taskRepository.deleteById(taskId);
    }
}
