package com.example.Employee.controller;

import com.example.Employee.model.EmployeeTask;
import com.example.Employee.service.EmployeeTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.employeeTaskService.createEmployeeTask(projectId, userId, employeeTask);
        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeTask>> getAllEmployeeTasks() {
        List<EmployeeTask> tasks = this.employeeTaskService.getAllEmployeeTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<EmployeeTask> getEmployeeTaskById(@PathVariable long taskId) {
        EmployeeTask task = this.employeeTaskService.getEmployeeTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<EmployeeTask>> getEmployeeTasksByProjectId(@PathVariable long projectId) {
        List<EmployeeTask> tasks = this.employeeTaskService.getEmployeeTasksByProjectId(projectId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Map<String, String>> updateEmployeeTask(
            @PathVariable long taskId,
            @RequestBody EmployeeTask employeeTask) {
        this.employeeTaskService.updateEmployeeTask(taskId, employeeTask);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee task has been updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}