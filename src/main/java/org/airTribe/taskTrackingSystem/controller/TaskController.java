package org.airTribe.taskTrackingSystem.controller;

import jakarta.validation.Valid;
import org.airTribe.taskTrackingSystem.annotate.ValidStatus;
import org.airTribe.taskTrackingSystem.dto.TaskRequestDto;
import org.airTribe.taskTrackingSystem.dto.TaskSearchRequestDto;
import org.airTribe.taskTrackingSystem.entity.*;
import org.airTribe.taskTrackingSystem.exception.InvalidRequestException;
import org.airTribe.taskTrackingSystem.exception.ProjectNotFoundException;
import org.airTribe.taskTrackingSystem.exception.TaskNotFoundException;
import org.airTribe.taskTrackingSystem.exception.UserNotFoundException;
import org.airTribe.taskTrackingSystem.service.ProjectService;
import org.airTribe.taskTrackingSystem.service.TaskService;
import org.airTribe.taskTrackingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService _taskService;

    @Autowired
    private UserService _userService;

    @Autowired
    ProjectService _projectService;

    @PostMapping("/")
    public ResponseEntity<Object> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto)
    {

        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            Project project =_projectService.getProjectById(taskRequestDto.getProjectId());

            Task task = Task.builder()
                            .title(taskRequestDto.getTitle())
                            .description(taskRequestDto.getDescription())
                            .status(Status.OPEN)
                            .dueDate(taskRequestDto.getDueDate())
                            .createdBy(user)
                            .project(project)
                            .CreatedDate(LocalDate.now()).build();

            if(taskRequestDto.getAssignedToUserId() != null)
            {
                task.setAssignedTo(_userService.getUserById(taskRequestDto.getAssignedToUserId()));
            }
            if(taskRequestDto.getPriority().equals(Priority.LOW.toString())) task.setPriority(Priority.LOW);
            else if(taskRequestDto.getPriority().equals(Priority.MEDIUM.toString())) task.setPriority(Priority.MEDIUM);
            else if(taskRequestDto.getPriority().equals(Priority.HIGH.toString())) task.setPriority(Priority.HIGH);

            _taskService.saveTask(task);

            response.put("Status",200);
            response.put("Task",task);
            return ResponseEntity.ok(response);

        }catch (ProjectNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","ProjectNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will Creating record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Object> getTaskById(@PathVariable long taskId)
    {

        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            Task task = _taskService.getTaskById(taskId);
            List<User> userList = _projectService.getUsersInProject(task.getProject().getProjectId());

            boolean isCreator = task.getCreatedBy().getEmail().equals(authEmail);
            boolean isAssigned = task.getAssignedTo().getEmail().equals(authEmail);
            boolean isInUserList = userList.stream().anyMatch(user -> user.getEmail().equals(authEmail));

            if(isCreator || isAssigned || isInUserList){

                response.put("Status",200);
                response.put("Task",task);
                return ResponseEntity.ok(response);
            }
            else {
                response.put("Status",403);
                response.put("message","you are not allowed to access this task");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }
        }catch (TaskNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","TaskNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Object> getTaskByProjectId(@PathVariable long projectId)
    {

        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            List<Task> taskByUser = _taskService.getAllTasksByProjectIdByUserID(projectId,user.getUserId());
            List<Task> taskForUser = _taskService.getAllTasksByProjectIdForUserID(projectId,user.getUserId());

            if(taskForUser.isEmpty() && taskByUser.isEmpty())
            {
                response.put("Status",400);
                response.put("message","TaskNotFoundException");
                return ResponseEntity.badRequest().body(response);
            }else {

                response.put("Status", 200);
                response.put("taskByUser", taskByUser);
                response.put("taskForUser", taskForUser);
                return ResponseEntity.ok(response);
            }
        }catch (TaskNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","TaskNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/allTask")
    public ResponseEntity<Object> getAllTaskCreatedByUser()
    {
        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            List<Task> taskByUser = _taskService.getAllTasksByUserID(user.getUserId());
            List<Task> taskForUser = _taskService.getAllTasksForUserID(user.getUserId());

            if(taskForUser.isEmpty() && taskByUser.isEmpty())
            {
                response.put("Status",400);
                response.put("message","TaskNotFoundException");
                return ResponseEntity.badRequest().body(response);
            }else {

                response.put("Status", 200);
                response.put("taskByUser", taskByUser);
                response.put("taskForUser", taskForUser);
                return ResponseEntity.ok(response);
            }
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PatchMapping("/{taskId}")
    public ResponseEntity<Object> createTask(@PathVariable long taskId,@RequestBody @Valid TaskRequestDto taskRequestDto)
    {

        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            Project project =_projectService.getProjectById(taskRequestDto.getProjectId());
            Task task = _taskService.getTaskById(taskId);

            boolean isCreator = task.getCreatedBy().getEmail().equals(authEmail);
            boolean isAssigned = task.getAssignedTo().getEmail().equals(authEmail);

            if(isCreator || isAssigned) {
                task.setTitle(taskRequestDto.getTitle());
                task.setDescription(taskRequestDto.getDescription());
                task.setDueDate(taskRequestDto.getDueDate());
                task.setProject(project);
                task.setUpdatedBy(user);
                task.setUpdateDate(LocalDate.now());

                if (taskRequestDto.getAssignedToUserId() != null) {
                    task.setAssignedTo(_userService.getUserById(taskRequestDto.getAssignedToUserId()));
                }
                if (taskRequestDto.getPriority().equals(Priority.LOW.toString())) task.setPriority(Priority.LOW);
                else if (taskRequestDto.getPriority().equals(Priority.MEDIUM.toString()))
                    task.setPriority(Priority.MEDIUM);
                else if (taskRequestDto.getPriority().equals(Priority.HIGH.toString())) task.setPriority(Priority.HIGH);

                response.put("Status", 200);
                response.put("Task", task);
                return ResponseEntity.ok(response);
            }else {
                response.put("Status",403);
                response.put("message","you are not allowed to access this task");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }

        }catch (ProjectNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","ProjectNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Object> updateStauts(@PathVariable long taskId,@RequestParam @ValidStatus String status)
    {

        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            Task task = _taskService.getTaskById(taskId);

            boolean isCreator = task.getCreatedBy().getEmail().equals(authEmail);
            boolean isAssigned = task.getAssignedTo().getEmail().equals(authEmail);

            if(isCreator || isAssigned) {
                if(status.equals(Status.IN_PROGRESS.toString())) task.setStatus(Status.IN_PROGRESS);
                else if(status.equals(Status.COMPLETED.toString())) task.setStatus(Status.COMPLETED);

                task.setUpdatedBy(user);
                task.setUpdateDate(LocalDate.now());
                Task updatedTask = _taskService.saveTask(task);
                response.put("Status",200);
                response.put("Task",updatedTask);
                return ResponseEntity.ok(response);
            }
            else {
                response.put("Status",403);
                response.put("message","you are not allowed to access this task");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }
        }catch (TaskNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","TaskNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<Object> updateStauts(@PathVariable long taskId,@PathVariable long useId)
    {

        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            Task task = _taskService.getTaskById(taskId);
            boolean isCreator = task.getCreatedBy().getEmail().equals(authEmail);
            boolean isAssigned = task.getAssignedTo().getEmail().equals(authEmail);

            if(isCreator || isAssigned) {
                task.setAssignedTo(_userService.getUserById(useId));
                task.setUpdatedBy(user);
                task.setUpdateDate(LocalDate.now());
                Task updatedTask = _taskService.saveTask(task);
                response.put("Status",200);
                response.put("Task",updatedTask);
                return ResponseEntity.ok(response);
            }
            else {
                response.put("Status",403);
                response.put("message","you are not allowed to access this task");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }
        }catch (TaskNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","TaskNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Object> searchTasks(@RequestBody @Valid TaskSearchRequestDto searchRequest) {

        Map<String, Object> response = new HashMap<>();

        try{
            List<Task> tasks = new ArrayList<>();
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            switch (searchRequest.getSearchfilter().toLowerCase())
            {
                case "status":
                    Status status;
                    if(searchRequest.getValue().equals(Status.IN_PROGRESS.toString())) status = Status.IN_PROGRESS;
                    else if(searchRequest.getValue().equals(Status.COMPLETED.toString())) status = (Status.COMPLETED);
                    else status = (Status.OPEN);
                    tasks = _taskService.findByStatusByUserId(status, user.getUserId());
                    break;
                case "title" :
                    tasks = _taskService.findByTitleByUserId(searchRequest.getValue(), user.getUserId());
                    break;
                case  "description" :
                    tasks = _taskService.findByDescriptionByUserId(searchRequest.getValue(), user.getUserId());
                    break;
                default:
                    throw new InvalidRequestException("invalid searchFiler");
            }
            if(tasks.isEmpty())
            {
                response.put("Status",400);
                response.put("message","TaskNotFoundException");
                return ResponseEntity.badRequest().body(response);
            }else {
                response.put("Status", 200);
                response.put("Task", tasks);
            }
        }catch (InvalidRequestException e)
        {
            throw new InvalidRequestException("invalid searchFiler");
        }
        catch (Exception e){}
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable long taskId)
    {
        Map<String, Object> response = new HashMap<>();
        try {
            String authEmail = _userService.getAutheticateUser();
            Task task = _taskService.getTaskById(taskId);
            boolean isCreator = task.getCreatedBy().getEmail().equals(authEmail);

            if(isCreator) {
                _taskService.deleteTaskById(taskId);
                response.put("Status", 200);
                response.put("message", "task delete Successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("Status", 403);
                response.put("message", "you are not allowed to delete this task");
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }
        }catch (TaskNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","TaskNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception E)
        {
            response.put("Status",500);
            response.put("message","error will deleting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }


}
