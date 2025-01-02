package com.example.Employee.service;

import com.example.Employee.model.EmployeeTask;
import com.example.Employee.model.Project;
import com.example.Employee.model.User;
import com.example.Employee.repository.EmployeeTaskRepository;
import com.example.Employee.repository.ProjectRepository;
import com.example.Employee.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeTaskServiceImpl implements EmployeeTaskService {


    private EmployeeTaskRepository employeeTaskRepository;

    private ProjectRepository projectRepository;

    private UserRepository userRepository;

    @Override
    @Transactional
    public EmployeeTask createEmployeeTask(long projectId, long userId, EmployeeTask employeeTask) {
        // Find and validate project
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Find and validate user
        User user = this.userRepository.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        // Set the relationships
        employeeTask.setProject(project);
        employeeTask.setUser(user);

        // Add task to project's employeeTasks list
        if (project.getEmployeeTasks() == null) {
            project.setEmployeeTasks(new ArrayList<>());
        }
        project.getEmployeeTasks().add(employeeTask);

        // Save and return the task
        return this.employeeTaskRepository.save(employeeTask);
    }

    @Override
    public List<EmployeeTask> getAllEmployeeTasks() {
        return this.employeeTaskRepository.findAll();
    }

    @Override
    public EmployeeTask getEmployeeTaskById(long employeeTaskId) {
        return this.employeeTaskRepository.findById(employeeTaskId)
                .orElseThrow(() -> new EntityNotFoundException("Employee Task not found with id: " + employeeTaskId));
    }

    @Override
    public List<EmployeeTask> getEmployeeTasksByProjectId(long projectId) {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
        return project.getEmployeeTasks();
    }

    @Override
    @Transactional
    public EmployeeTask updateEmployeeTask(long employeeTaskId, EmployeeTask employeeTask) {
        // Find the existing task
        EmployeeTask existingTask = this.employeeTaskRepository.findById(employeeTaskId)
                .orElseThrow(() -> new EntityNotFoundException("Employee Task not found with id: " + employeeTaskId));

        // Update the fields
        existingTask.setCurrentTask(employeeTask.getCurrentTask());
        existingTask.setPendingTask(employeeTask.getPendingTask());
        existingTask.setTodoTask(employeeTask.getTodoTask());
        existingTask.setCurrentTaskCompletionDate(employeeTask.getCurrentTaskCompletionDate());
        existingTask.setPendingTaskCompletionDate(employeeTask.getPendingTaskCompletionDate());
        existingTask.setReasonForPendingTask(employeeTask.getReasonForPendingTask());
        existingTask.setMerged(employeeTask.isMerged());

        // Save and return the updated task
        return this.employeeTaskRepository.save(existingTask);
    }

}