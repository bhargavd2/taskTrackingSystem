package org.airTribe.taskTrackingSystem.controller;



import jakarta.validation.Valid;
import org.airTribe.taskTrackingSystem.dto.ProjectRequestDto;
import org.airTribe.taskTrackingSystem.entity.Project;
import org.airTribe.taskTrackingSystem.entity.ProjectInvitation;
import org.airTribe.taskTrackingSystem.entity.User;
import org.airTribe.taskTrackingSystem.exception.ProjectNotFoundException;
import org.airTribe.taskTrackingSystem.exception.UserNotFoundException;
import org.airTribe.taskTrackingSystem.service.ProjectService;
import org.airTribe.taskTrackingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Project")
public class ProjectController {

    @Autowired
    ProjectService _projectService;

    @Autowired
    private UserService _userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable long projectId)
    {
        Map<String, Object> response = new HashMap<>();
        try {
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            Project project = _projectService.getProjectById(projectId);
            List<User> userList = _projectService.getUsersInProject(projectId);
            if((project.getCreatedBy().getEmail().equals(authEmail))||
                    (userList.contains(user))){
                response.put("Status", 200);
                response.put("Project", project);
            }else {
                response.put("Status",403);
                response.put("message","you don't what access to project!!!!");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }
        }catch (ProjectNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","ProjectNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Object> getUsersByProject(@PathVariable long projectId)
    {
        Map<String, Object> response = new HashMap<>();
        try {
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            Project project = _projectService.getProjectById(projectId);
            List<User> userList = _projectService.getUsersInProject(projectId);
            if((project.getCreatedBy().getEmail().equals(authEmail))||
                    (userList.contains(user))) {
                response.put("Status", 200);
                response.put("Users", userList);
                return ResponseEntity.ok(response);
            }else {
                response.put("Status",403);
                response.put("message","you don't what access to project!!!!");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }

        }catch (ProjectNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","ProjectNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/invitation")
    public ResponseEntity<Object> getInvitationsForUser()
    {
        Map<String, Object> response = new HashMap<>();
        try {
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            List<ProjectInvitation> projectInvitationList = _projectService.getProjectInvitationForUser(user);
            response.put("Status", 200);
            response.put("ProjectInvitation", projectInvitationList);
            return ResponseEntity.ok(response);
        }catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/invitation/sent")
    public ResponseEntity<Object> getInvitationsByUser()
    {
        Map<String, Object> response = new HashMap<>();
        try {
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);
            List<ProjectInvitation> projectInvitationList = _projectService.getProjectInvitationByUser(user);
            response.put("Status", 200);
            response.put("ProjectInvitation", projectInvitationList);
            return ResponseEntity.ok(response);
        }catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will getting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> saveProject(@RequestBody @Valid ProjectRequestDto projectRequestDto)
    {
        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);

            Project project = _projectService.saveProject(projectRequestDto,user);

            response.put("Status",201);
            response.put("Project",project);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will creating record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProject(@RequestBody @Valid ProjectRequestDto projectRequestDto,
                                                @PathVariable long projectId)
    {
        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);

            Project project = _projectService.getProjectById(projectId);

            if(project.getCreatedBy().getUserId() == user.getUserId())
            {
                Project responseProject = _projectService.updateProject(project,projectRequestDto,user);
                response.put("Status",200);
                response.put("Project",project);
                return ResponseEntity.ok(response);
            }else{
                response.put("Status",403);
                response.put("message","not allowed to update Project as you are not the Owner!!!!");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }

        }
        catch (ProjectNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","ProjectNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will updating record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/{id}/invite/{userId}")
    public  ResponseEntity<Object> inviteToProject(@PathVariable long projectId, @PathVariable Long userId)
    {
        Map<String, Object> response = new HashMap<>();

        try{

            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);

            User InvetaionUser = _userService.getUserById(userId);

            Project project = _projectService.getProjectById(projectId);

            if(project.getCreatedBy().getUserId() == user.getUserId())
            {
                ProjectInvitation projectInvitation = _projectService.invite(project,user,InvetaionUser);
                response.put("Status",201);
                response.put("Project",projectInvitation);
                return ResponseEntity.ok(response);
            }else{
                response.put("Status",403);
                response.put("message","not allowed to update Project as you are not the Owner!!!!");
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
        }catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will creating Invite Request");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteProject(@PathVariable long projectId)
    {
        Map<String, Object> response = new HashMap<>();
        try{
            String authEmail = _userService.getAutheticateUser();
            User user = _userService.getUserByEmail(authEmail);

            Project project = _projectService.getProjectById(projectId);

            if(project.getCreatedBy().getEmail().equals(authEmail))
            {
                _projectService.deleteProject(projectId);
                response.put("Status",200);
                response.put("message","project delete Successfully");
                return ResponseEntity.ok(response);
            }else{
                response.put("Status",403);
                response.put("message","not allowed to delete Project as you are not the Owner!!!!");
                return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
            }

        }catch (ProjectNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","ProjectNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will deleting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
