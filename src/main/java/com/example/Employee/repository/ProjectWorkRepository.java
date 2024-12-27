package com.example.Employee.repository;

import com.example.Employee.model.ProjectWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectWorkRepository extends JpaRepository<ProjectWork, Long> {
}

