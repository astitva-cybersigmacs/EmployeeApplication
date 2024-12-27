package com.example.Employee.service;

import com.example.Employee.model.Project;
import com.example.Employee.model.ProjectWork;
import com.example.Employee.repository.ProjectRepository;
import com.example.Employee.repository.ProjectWorkRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectWorkServiceImpl implements ProjectWorkService {

    @Autowired
    private ProjectWorkRepository projectWorkRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    @Transactional
    public ProjectWork saveProjectWork(long projectId, ProjectWork projectWork) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        projectWork.setProject(project);

        if (project.getProjectWorks() == null) {
            project.setProjectWorks(new ArrayList<>());
        }

        project.getProjectWorks().add(projectWork);

        return projectWorkRepository.save(projectWork);
    }

    @Override
    @Transactional
    public void deleteProjectWork(long projectId, long workId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectWork projectWork = projectWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException("Project work not found with id: " + workId));

        // Verify that the project work belongs to the specified project
        if (projectWork.getProject().getProjectId() != projectId) {
            throw new IllegalArgumentException("Project work does not belong to the specified project");
        }

        // Remove the work from the project's list
        project.getProjectWorks().remove(projectWork);

        // Delete the project work
        projectWorkRepository.delete(projectWork);
    }

    @Override
    public List<ProjectWork> getProjectWorksByProjectId(long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        return project.getProjectWorks();
    }
}
