package com.example.demo.service;

import com.example.demo.model.Student;

public interface StudentService {
    Student register(String username, String password, String email);
    Student findByUsername(String username);
    Student findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
