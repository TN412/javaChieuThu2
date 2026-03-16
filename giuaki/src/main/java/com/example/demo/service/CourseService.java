package com.example.demo.service;

import com.example.demo.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    Page<Course> findAll(int page, int size);
    Page<Course> searchByName(String name, int page, int size);
    Course findById(Long id);
    Course save(Course course, MultipartFile imageFile);
    Course update(Course course, MultipartFile imageFile);
    void delete(Long id);
    List<Course> findAll();
}
