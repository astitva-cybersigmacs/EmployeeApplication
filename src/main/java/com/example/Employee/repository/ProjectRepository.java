package com.example.Employee.repository;



import com.example.Employee.model.Project;
import com.example.Employee.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);
    List<Project> findByStatus(ProjectStatus status);

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN p.projectWorks pw " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR CAST(pw.date AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(pw.currentWorkStartDate AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(pw.currentWorkCompletionDate AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(pw.currentWorkDeployDate AS string) LIKE CONCAT('%', :keyword, '%')")
    List<Project> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p, pw.date, pw.currentWorkStartDate, " +
            "pw.currentWorkCompletionDate, pw.currentWorkDeployDate " +
            "FROM Project p LEFT JOIN p.projectWorks pw " +
            "ORDER BY pw.date DESC, pw.currentWorkStartDate DESC, " +
            "pw.currentWorkCompletionDate DESC, pw.currentWorkDeployDate DESC")
    List<Object[]> findAllProjectsOrderByDateDesc();
}
