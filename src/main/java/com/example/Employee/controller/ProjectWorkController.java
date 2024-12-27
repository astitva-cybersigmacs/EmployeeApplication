package com.example.Employee.controller;

import com.example.Employee.model.ProjectWork;
import com.example.Employee.service.ProjectWorkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;


import java.util.Date;
import java.util.Map;

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

    @DeleteMapping("/{workId}")
    public ResponseEntity<?> deleteProjectWork(
            @PathVariable long projectId,
            @PathVariable long workId) {
        projectWorkService.deleteProjectWork(projectId, workId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<ProjectWork>> getProjectWorksByProjectId(
            @PathVariable long projectId) {
        List<ProjectWork> projectWorks = projectWorkService.getProjectWorksByProjectId(projectId);
        return ResponseEntity.ok(projectWorks);
    }

    @PutMapping("/{workId}")
    public ResponseEntity<ProjectWork> updateProjectWorkDates(
            @PathVariable long projectId,
            @PathVariable long workId,
            @RequestBody String requestBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(dateFormat);

        // Configure ObjectMapper to handle timezone
        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));

        Map<String, Date> dates = objectMapper.readValue(requestBody, new TypeReference<Map<String, Date>>() {});

        ProjectWork updatedWork = projectWorkService.updateProjectWorkDates(projectId, workId, dates);
        return ResponseEntity.ok(updatedWork);
    }


}
