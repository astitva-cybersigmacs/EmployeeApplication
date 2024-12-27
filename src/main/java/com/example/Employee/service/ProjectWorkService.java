package com.example.Employee.service;

import com.example.Employee.model.ProjectWork;

public interface ProjectWorkService {
    ProjectWork saveProjectWork(long projectId, ProjectWork projectWork);
}
