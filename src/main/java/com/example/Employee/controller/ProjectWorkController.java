package com.example.Employee.controller;

import com.example.Employee.model.ProjectWork;
import com.example.Employee.service.ProjectWorkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects/{projectId}/works")
public class ProjectWorkController {

    @Autowired
    private final ProjectWorkService projectWorkService;

    @PostMapping
    public ResponseEntity<ProjectWork> createProjectWork(
            @PathVariable long projectId,
            @RequestBody ProjectWork projectWork) {
        ProjectWork savedWork = projectWorkService.saveProjectWork(projectId, projectWork);
        return new ResponseEntity<>(savedWork, HttpStatus.CREATED);
    }
}
