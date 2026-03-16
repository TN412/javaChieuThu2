package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment enroll(Student student, Course course) {
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Bạn đã đăng ký học phần này rồi!");
        }
        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollDate(LocalDate.now())
                .build();
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> findByStudent(Student student) {
        return enrollmentRepository.findByStudent(student);
    }

    @Override
    public boolean isEnrolled(Student student, Course course) {
        return enrollmentRepository.existsByStudentAndCourse(student, course);
    }
}
