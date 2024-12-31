package com.example.Employee.controller;

import com.example.Employee.model.EmployeeTask;
import com.example.Employee.service.EmployeeTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("employeeTask")
public class EmployeeTaskController {

    private EmployeeTaskService employeeTaskService;

    @PostMapping("/project/{projectId}/user/{userId}")
    public ResponseEntity<String> createEmployeeTask(
            @PathVariable long projectId,
            @PathVariable long userId,
            @RequestBody EmployeeTask employeeTask) {
        EmployeeTask savedTask = this.employeeTaskService.createEmployeeTask(projectId, userId, employeeTask);
        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

}