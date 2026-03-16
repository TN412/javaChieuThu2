package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.EnrollmentService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping({"/", "/home", "/courses"})
    public String home(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        Page<Course> coursePage;
        if (keyword != null && !keyword.isEmpty()) {
            coursePage = courseService.searchByName(keyword, page, 5);
        } else {
            coursePage = courseService.findAll(page, 5);
        }

        // Tính danh sách các học phần đã đăng ký của sinh viên
        Map<Long, Boolean> enrolledMap = new HashMap<>();
        if (userDetails != null) {
            try {
                Student student = studentService.findByUsername(userDetails.getUsername());
                List<Enrollment> enrollments = enrollmentService.findByStudent(student);
                for (Enrollment e : enrollments) {
                    enrolledMap.put(e.getCourse().getId(), true);
                }
            } catch (Exception ignored) {}
        }

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("enrolledMap", enrolledMap);

        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403";
    }
}
