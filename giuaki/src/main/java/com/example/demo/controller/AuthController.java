package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            if ("oauth2".equals(error)) {
                model.addAttribute("errorMessage", "Đăng nhập Google thất bại. Vui lòng thử lại.");
            } else {
                model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            }
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Bạn đã đăng xuất thành công!");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("student", new Student());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String email,
                           Model model) {

        if (studentService.existsByUsername(username)) {
            model.addAttribute("usernameError", "Tên đăng nhập đã tồn tại!");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "auth/register";
        }

        if (studentService.existsByEmail(email)) {
            model.addAttribute("emailError", "Email đã được sử dụng!");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "auth/register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("passwordError", "Mật khẩu xác nhận không khớp!");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "auth/register";
        }

        studentService.register(username, password, email);
        return "redirect:/login?registered=true";
    }
}
