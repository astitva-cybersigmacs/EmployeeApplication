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

import java.util.*;

@Service
@AllArgsConstructor
public class ProjectWorkServiceImpl implements ProjectWorkService {


    private ProjectWorkRepository projectWorkRepository;


    private ProjectRepository projectRepository;


    @Override
    @Transactional
    public ProjectWork saveProjectWork(long projectId, ProjectWork projectWork) {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        projectWork.setProject(project);

        if (project.getProjectWorks() == null) {
            project.setProjectWorks(new ArrayList<>());
        }

        project.getProjectWorks().add(projectWork);

        return this.projectWorkRepository.save(projectWork);
    }

    @Override
    @Transactional
    public void deleteProjectWork(long projectId, long workId) {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectWork projectWork = this.projectWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException("Project work not found with id: " + workId));

        // Verify that the project work belongs to the specified project
        if (projectWork.getProject().getProjectId() != projectId) {
            throw new IllegalArgumentException("Project work does not belong to the specified project");
        }

        // Remove the work from the project's list
        project.getProjectWorks().remove(projectWork);

        // Delete the project work
        this.projectWorkRepository.delete(projectWork);
    }

    @Override
    public List<ProjectWork> getProjectWorksByProjectId(long projectId) {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        return project.getProjectWorks();
    }

    @Override
    @Transactional
    public ProjectWork updateProjectWorkDates(long projectId, long workId, Map<String, Date> dates) {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectWork projectWork = this.projectWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException("Project work not found with id: " + workId));

        if (projectWork.getProject().getProjectId() != projectId) {
            throw new IllegalArgumentException("Project work does not belong to the specified project");
        }

        // Set time to noon UTC to avoid timezone issues
        if (dates.containsKey("currentWorkCompletionDate")) {
            Date completionDate = dates.get("currentWorkCompletionDate");
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTime(completionDate);
            cal.set(Calendar.HOUR_OF_DAY, 12);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            projectWork.setCurrentWorkCompletionDate(cal.getTime());
        }

        if (dates.containsKey("currentWorkStartDate")) {
            Date startDate = dates.get("currentWorkStartDate");
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTime(startDate);
            cal.set(Calendar.HOUR_OF_DAY, 12);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            projectWork.setCurrentWorkStartDate(cal.getTime());
        }

        if (dates.containsKey("currentWorkDeployDate")) {
            Date deployDate = dates.get("currentWorkDeployDate");
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTime(deployDate);
            cal.set(Calendar.HOUR_OF_DAY, 12);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            projectWork.setCurrentWorkDeployDate(cal.getTime());
        }

        return this.projectWorkRepository.save(projectWork);
    }
}