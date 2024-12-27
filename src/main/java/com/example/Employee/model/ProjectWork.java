package com.example.Employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "project_work")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectWorkId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String todoWork;
    private String pendingWork;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentWorkCompletionDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentWorkStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentWorkDeployDate;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;
}
