package com.example.Employee.controller;

import com.example.Employee.model.Project;
import com.example.Employee.model.ProjectStatus;
import com.example.Employee.repository.ProjectRepository;
import com.example.Employee.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("projects")
public class ProjectController {

    private ProjectRepository projectRepository;
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createProject(@RequestBody Project project) {
        // First check if project exists
        Project existingProject = this.projectRepository.findByName(project.getName());
        if (existingProject != null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Project with name '" + project.getName() + "' already exists");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        this.projectService.saveProject(project);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Project Report has been created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/name")
    @SneakyThrows
    public ResponseEntity<?> getProjectByName(@RequestParam String name) {
        Project project = this.projectService.getProjectByName(name);

        if (project == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "The project with name '" + name + "' is not present");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(project);
    }

    @GetMapping("/status/{status}")
    @SneakyThrows
    public ResponseEntity<?> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<Project> projects = this.projectService.getProjectsByStatus(status);

        if (projects.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No projects found with status '" + status + "'");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/search")
    @SneakyThrows
    public ResponseEntity<?> searchProjects(@RequestParam String keyword) {
        List<Project> projects = this.projectService.searchProjectsByKeyword(keyword);

        if (projects.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No projects found matching keyword '" + keyword + "'");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/sorted-by-date")
    @SneakyThrows
    public ResponseEntity<?> getProjectsSortedByDate() {
        List<Project> projects = this.projectService.getAllProjectsSortedByDateDesc();

        if (projects.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No projects found in the system");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{projectId}")
    @SneakyThrows
    public ResponseEntity<Void> deleteProject(@PathVariable long projectId) {
        if (projectId <= 0) {
            throw new IllegalArgumentException("Invalid project ID. It must be greater than 0.");
        }
        this.projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable long projectId, @RequestBody Project project) {
        if (projectId <= 0) {
            throw new IllegalArgumentException("Invalid project ID. It must be greater than 0.");
        }
        if (project == null || project.getName() == null || project.getName().isEmpty()) {
            throw new IllegalArgumentException("Project details are invalid. Name must not be null or empty.");
        }
        Project updatedProject = this.projectService.updateProject(projectId, project);
        return ResponseEntity.ok(updatedProject);
    }
}
