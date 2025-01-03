package com.example.Employee.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private long projectId;

    @Column(unique = true, nullable = false)
    private String name;

    private String details;

    private String description;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProjectTeamMember> teamMembers;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProjectWork> projectWorks;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<EmployeeTask> employeeTasks;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;


}
