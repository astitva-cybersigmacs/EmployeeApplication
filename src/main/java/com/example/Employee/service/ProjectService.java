package com.example.Employee.service;

import com.example.Employee.model.Project;
import com.example.Employee.model.ProjectStatus;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ProjectService {
    Project saveProject(Project project);
    Project getProjectByName(String name);
    List<Project> getProjectsByStatus(ProjectStatus status);
    List<Project> searchProjectsByKeyword(String keyword);
    List<Project> getAllProjectsSortedByDateDesc();
    void deleteProject(long projectId);
    Project updateProject(long projectId, Project project);
}
