package com.example.Employee.model;

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

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.ALL}, orphanRemoval = true)
    private List<ProjectTeamMember> teamMembers;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.ALL}, orphanRemoval = true)
    private List<ProjectWork> projectWorks;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.ALL}, orphanRemoval = true)
    private List<EmployeeTask> employeeTasks;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;


}
