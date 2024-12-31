package com.example.Employee.controller;

import com.example.Employee.model.ProjectWork;
import com.example.Employee.service.ProjectWorkService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
@RequestMapping("/projectWork")
public class ProjectWorkController {


    private final ProjectWorkService projectWorkService;

    @PostMapping("/{projectId}")
    @SneakyThrows
    public ResponseEntity<Map<String, Object>> createProjectWork(
            @PathVariable long projectId,
            @RequestBody ProjectWork projectWork) {

        // Validation for projectId and projectWork
        if (projectId <= 0 || projectWork == null) {
            throw new IllegalArgumentException("Invalid project ID. It must be greater than 0.");
        }

        ProjectWork savedWork = this.projectWorkService.saveProjectWork(projectId, projectWork);

        // Prepare the response with success message and saved project work
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Project Work created successfully");
        response.put("projectWork", savedWork);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}/{workId}")
    public ResponseEntity<?> deleteProjectWork(
            @PathVariable long projectId,
            @PathVariable long workId) {
        if (projectId <= 0) {
            return ResponseEntity.badRequest().body("Invalid project ID. It must be greater than 0.");
        }
        if (workId <= 0) {
            return ResponseEntity.badRequest().body("Invalid work ID. It must be greater than 0.");
        }
        this.projectWorkService.deleteProjectWork(projectId, workId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectWork>> getProjectWorksByProjectId(
            @PathVariable long projectId) {
        List<ProjectWork> projectWorks = this.projectWorkService.getProjectWorksByProjectId(projectId);
        return ResponseEntity.ok(projectWorks);
    }

    @PutMapping("/{projectId}/{workId}")
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

        ProjectWork updatedWork = this.projectWorkService.updateProjectWorkDates(projectId, workId, dates);
        return ResponseEntity.ok(updatedWork);
    }


}
