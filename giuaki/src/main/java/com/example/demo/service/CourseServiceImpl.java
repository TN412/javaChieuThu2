package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Value("${app.upload.dir:uploads/courses}")
    private String uploadDir;

    @Override
    public Page<Course> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable);
    }

    @Override
    public Page<Course> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần với id: " + id));
    }

    @Override
    public Course save(Course course, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            course.setImage(imagePath);
        }
        return courseRepository.save(course);
    }

    @Override
    public Course update(Course course, MultipartFile imageFile) {
        Course existing = findById(course.getId());
        existing.setName(course.getName());
        existing.setCredits(course.getCredits());
        existing.setLecturer(course.getLecturer());
        existing.setCategory(course.getCategory());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            existing.setImage(imagePath);
        }
        return courseRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    private String saveImage(MultipartFile imageFile) {
        try {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
            String extension = originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String filename = UUID.randomUUID() + extension;
            Files.copy(imageFile.getInputStream(), uploadPath.resolve(filename));
            return "/" + uploadDir + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu ảnh: " + e.getMessage());
        }
    }
}
