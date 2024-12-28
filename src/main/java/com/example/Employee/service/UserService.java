package com.example.Employee.service;

import com.example.Employee.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(long userId);
    User getUserByEmail(String email);
}
