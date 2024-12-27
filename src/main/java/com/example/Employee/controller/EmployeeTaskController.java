package com.example.Employee.controller;

import com.example.Employee.model.EmployeeTask;
import com.example.Employee.service.EmployeeTaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects/{projectId}/users/{userId}/tasks")
public class EmployeeTaskController {

    @Autowired
    private final EmployeeTaskService employeeTaskService;

    @PostMapping
    public ResponseEntity<EmployeeTask> createEmployeeTask(
            @PathVariable long projectId,
            @PathVariable long userId,
            @RequestBody EmployeeTask employeeTask) {
        EmployeeTask savedTask = employeeTaskService.createEmployeeTask(projectId, userId, employeeTask);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }
}