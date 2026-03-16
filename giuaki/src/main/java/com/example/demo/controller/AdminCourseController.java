package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/courses")
    public String listCourses(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword,
                               Model model) {
        Page<Course> coursePage;
        if (keyword != null && !keyword.isEmpty()) {
            coursePage = courseService.searchByName(keyword, page, 10);
        } else {
            coursePage = courseService.findAll(page, 10);
        }
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "admin/courses/list";
    }

    @GetMapping("/courses/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/courses/add";
    }

    @PostMapping("/courses/add")
    public String addCourse(@ModelAttribute Course course,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                            RedirectAttributes redirectAttributes) {
        try {
            if (course.getCategory() != null && course.getCategory().getId() != null) {
                Category category = categoryService.findById(course.getCategory().getId());
                course.setCategory(category);
            }
            courseService.save(course, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm học phần thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/courses/edit";
    }

    @PostMapping("/courses/edit/{id}")
    public String editCourse(@PathVariable Long id,
                             @ModelAttribute Course course,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {
        try {
            course.setId(id);
            if (course.getCategory() != null && course.getCategory().getId() != null) {
                Category category = categoryService.findById(course.getCategory().getId());
                course.setCategory(category);
            }
            courseService.update(course, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật học phần thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa học phần thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }
}
