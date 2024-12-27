package com.example.Employee.service;

import com.example.Employee.model.EmployeeTask;

public interface EmployeeTaskService {
    EmployeeTask createEmployeeTask(long projectId, long userId, EmployeeTask employeeTask);
}
