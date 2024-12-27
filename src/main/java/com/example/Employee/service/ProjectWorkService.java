package com.example.Employee.service;

import com.example.Employee.model.ProjectWork;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProjectWorkService {
    ProjectWork saveProjectWork(long projectId, ProjectWork projectWork);
    void deleteProjectWork(long projectId, long workId);
    List<ProjectWork> getProjectWorksByProjectId(long projectId);
    ProjectWork updateProjectWorkDates(long projectId, long workId, Map<String, Date> dates);
}
