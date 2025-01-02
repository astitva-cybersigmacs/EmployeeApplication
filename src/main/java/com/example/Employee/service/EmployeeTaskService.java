package com.example.Employee.service;

import com.example.Employee.model.EmployeeTask;

import java.util.List;

public interface EmployeeTaskService {
    EmployeeTask createEmployeeTask(long projectId, long userId, EmployeeTask employeeTask);

    List<EmployeeTask> getAllEmployeeTasks();
    EmployeeTask getEmployeeTaskById(long employeeTaskId);
    List<EmployeeTask> getEmployeeTasksByProjectId(long projectId);
}
