package org.airTribe.taskTrackingSystem.service;

import org.airTribe.taskTrackingSystem.dto.ProjectRequestDto;
import org.airTribe.taskTrackingSystem.entity.*;
import org.airTribe.taskTrackingSystem.exception.ProjectNotFoundException;
import org.airTribe.taskTrackingSystem.repository.ProjectInvitationRepository;
import org.airTribe.taskTrackingSystem.repository.ProjectRepository;
import org.airTribe.taskTrackingSystem.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository _projectRepository;

    @Autowired
    private UserProjectRepository _userProjectRepository;

    @Autowired
    private ProjectInvitationRepository _projectInvitationRepository;

    public Project getProjectById(long projectId)
    {
        Optional<Project> project = _projectRepository.findById(projectId);
        if(project.isEmpty()) throw new ProjectNotFoundException("project not found");
        return project.get();
    }

    public List<User> getUsersInProject(Long projectId){
        List<UserProject> userProjects = _userProjectRepository.findByProject_ProjectId(projectId);
        return userProjects.stream().map(UserProject::getUser).collect(Collectors.toList());
    }

    public List<ProjectInvitation> getProjectInvitationForUser(User user){
        return _projectInvitationRepository.findByInvitationTo(user);
    }

    public List<ProjectInvitation> getProjectInvitationByUser(User user){
        return _projectInvitationRepository.findByCreatedBy(user);
    }

    public Project saveProject(ProjectRequestDto projectRequestDto,User user)
    {
        Project project = Project.builder()
                .name(projectRequestDto.getName())
                .description(projectRequestDto.getDescription())
                .createdBy(user)
                .CreatedDate(LocalDate.now())
                .build();

        return _projectRepository.save(project);
    }

    public Project updateProject(Project project,ProjectRequestDto projectRequestDto,User user)
    {
        Project saveProject = Project.builder()
                .projectId(project.getProjectId())
                .name(projectRequestDto.getName())
                .description(projectRequestDto.getDescription())
                .createdBy(project.getCreatedBy())
                .CreatedDate(project.getCreatedDate())
                .updatedBy(user)
                .UpdateDate(LocalDate.now())
                .build();

        return _projectRepository.save(project);
    }

    public ProjectInvitation invite(Project project, User user, User InvetaionUser)
    {
        String inviteCode = UUID.randomUUID().toString();

        ProjectInvitation projectInvitation = ProjectInvitation.builder()
                .inviteCode(inviteCode)
                .project(project)
                .invitationTo(InvetaionUser)
                .createdBy(user)
                .CreatedDate(LocalDate.now())
                .status(InvitationStatus.PENDING)
                .build();

        return _projectInvitationRepository.save(projectInvitation);
    }

    public void deleteProject(Long projectId)
    {
        _projectRepository.deleteById(projectId);
    }
}
