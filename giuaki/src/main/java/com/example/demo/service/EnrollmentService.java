package com.example.demo.service;

import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.model.Course;

import java.util.List;

public interface EnrollmentService {
    Enrollment enroll(Student student, Course course);
    List<Enrollment> findByStudent(Student student);
    boolean isEnrolled(Student student, Course course);
}
