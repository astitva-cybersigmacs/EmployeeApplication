package com.example.Employee.service;

import com.example.Employee.model.ProjectWork;

import java.util.List;

public interface ProjectWorkService {
    ProjectWork saveProjectWork(long projectId, ProjectWork projectWork);
    void deleteProjectWork(long projectId, long workId);
    List<ProjectWork> getProjectWorksByProjectId(long projectId);
}
