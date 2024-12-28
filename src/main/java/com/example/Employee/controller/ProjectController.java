package com.example.Employee.controller;



import com.example.Employee.model.Project;
import com.example.Employee.model.ProjectStatus;
import com.example.Employee.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectService.saveProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping("/name/{name}")
    @SneakyThrows
    public ResponseEntity<Project> getProjectByName(@PathVariable String name) {
        Project project = projectService.getProjectByName(name);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/status/{status}")
    @SneakyThrows
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<Project> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/search")
    @SneakyThrows
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String keyword) {
        List<Project> projects = projectService.searchProjectsByKeyword(keyword);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/sorted-by-date")
    @SneakyThrows
    public ResponseEntity<List<Project>> getProjectsSortedByDate() {
        List<Project> projects = projectService.getAllProjectsSortedByDateDesc();
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{projectId}")
    @SneakyThrows
    public ResponseEntity<Void> deleteProject(@PathVariable long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable long projectId, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(projectId, project);
        return ResponseEntity.ok(updatedProject);
    }
}
