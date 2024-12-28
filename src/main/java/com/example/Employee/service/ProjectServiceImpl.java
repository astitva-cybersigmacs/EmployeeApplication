package com.example.Employee.service;

import com.example.Employee.model.*;
import com.example.Employee.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public Project saveProject(Project project) {
        // Set bidirectional relationships
        if (project.getTeamMembers() != null) {
            project.getTeamMembers().forEach(member -> member.setProject(project));
        }

        if (project.getProjectWorks() != null) {
            project.getProjectWorks().forEach(work -> work.setProject(project));
        }

        if (project.getEmployeeTasks() != null) {
            project.getEmployeeTasks().forEach(task -> task.setProject(project));
        }
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectByName(String name) {
        Project project = projectRepository.findByName(name);
        if (project == null) {
            throw new EntityNotFoundException("Project not found with name: " + name);
        }
        return project;
    }

    @Override
    public List<Project> getProjectsByStatus(ProjectStatus status) {
        List<Project> projects = projectRepository.findByStatus(status);
        if (projects.isEmpty()) {
            throw new EntityNotFoundException("No projects found with status: " + status);
        }
        return projects;
    }

    @Override
    public List<Project> searchProjectsByKeyword(String keyword) {
        List<Project> projects = projectRepository.searchByKeyword(keyword);
        if (projects.isEmpty()) {
            throw new EntityNotFoundException("No projects found matching keyword: " + keyword);
        }
        return projects;
    }

    @Override
    public List<Project> getAllProjectsSortedByDateDesc() {
        List<Object[]> results = projectRepository.findAllProjectsOrderByDateDesc();
        if (results.isEmpty()) {
            throw new EntityNotFoundException("No projects found");
        }

        // Extract just the Project objects from the results
        return results.stream()
                .map(result -> (Project) result[0])
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteProject(long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Delete all related entities
        projectRepository.delete(project);
    }

    @Override
    @Transactional
    public Project updateProject(long projectId, Project project) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Update basic fields
        existingProject.setName(project.getName());
        existingProject.setDetails(project.getDetails());
        existingProject.setDescription(project.getDescription());
        existingProject.setStatus(project.getStatus());

        // Update team members
        if (project.getTeamMembers() != null) {
            existingProject.getTeamMembers().clear();
            project.getTeamMembers().forEach(member -> {
                member.setProject(existingProject);
                if (member.getUserList() != null) {
                    member.getUserList().forEach(user -> user.setProjectTeamMember(member));
                }
            });
            existingProject.getTeamMembers().addAll(project.getTeamMembers());
        }

        // Update project works
        if (project.getProjectWorks() != null) {
            existingProject.getProjectWorks().clear();
            project.getProjectWorks().forEach(work -> work.setProject(existingProject));
            existingProject.getProjectWorks().addAll(project.getProjectWorks());
        }

        // Update employee tasks
        if (project.getEmployeeTasks() != null) {
            existingProject.getEmployeeTasks().clear();
            project.getEmployeeTasks().forEach(task -> task.setProject(existingProject));
            existingProject.getEmployeeTasks().addAll(project.getEmployeeTasks());
        }

        return projectRepository.save(existingProject);
    }
}
