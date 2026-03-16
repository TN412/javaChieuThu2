package com.example.demo.config;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.model.Role;
import com.example.demo.model.Student;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 1. Tạo roles nếu chưa có
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));
        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_STUDENT").build()));

        // 2. Tạo tài khoản ADMIN mặc định
        if (!studentRepository.existsByUsername("admin")) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            Student admin = Student.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@example.com")
                    .roles(adminRoles)
                    .build();
            studentRepository.save(admin);
            System.out.println("=== Tạo tài khoản ADMIN mặc định: admin / admin123 ===");
        }

        // 3. Tạo tài khoản STUDENT mẫu
        if (!studentRepository.existsByUsername("student1")) {
            Set<Role> studentRoles = new HashSet<>();
            studentRoles.add(studentRole);
            Student student = Student.builder()
                    .username("student1")
                    .password(passwordEncoder.encode("student123"))
                    .email("student1@example.com")
                    .roles(studentRoles)
                    .build();
            studentRepository.save(student);
            System.out.println("=== Tạo tài khoản STUDENT mẫu: student1 / student123 ===");
        }

        // 4. Tạo danh mục mẫu
        if (categoryRepository.count() == 0) {
            Category cntt = categoryRepository.save(Category.builder().name("Công nghệ thông tin").build());
            Category kinh = categoryRepository.save(Category.builder().name("Kinh tế").build());
            Category ngoaingu = categoryRepository.save(Category.builder().name("Ngoại ngữ").build());
            Category khtn = categoryRepository.save(Category.builder().name("Khoa học tự nhiên").build());
            Category xhnh = categoryRepository.save(Category.builder().name("Xã hội nhân văn").build());

            // 5. Tạo học phần mẫu
            courseRepository.save(Course.builder().name("Lập Trình Java").credits(3)
                    .lecturer("TS. Nguyễn Văn A").category(cntt).build());
            courseRepository.save(Course.builder().name("Cơ Sở Dữ Liệu").credits(3)
                    .lecturer("ThS. Trần Thị B").category(cntt).build());
            courseRepository.save(Course.builder().name("Mạng Máy Tính").credits(3)
                    .lecturer("PGS. Lê Văn C").category(cntt).build());
            courseRepository.save(Course.builder().name("Trí Tuệ Nhân Tạo").credits(3)
                    .lecturer("GS. Phạm Văn D").category(cntt).build());
            courseRepository.save(Course.builder().name("Kỹ Thuật Phần Mềm").credits(3)
                    .lecturer("TS. Hoàng Thị E").category(cntt).build());
            courseRepository.save(Course.builder().name("Kinh Tế Vi Mô").credits(2)
                    .lecturer("TS. Vũ Văn F").category(kinh).build());
            courseRepository.save(Course.builder().name("Quản Trị Kinh Doanh").credits(3)
                    .lecturer("ThS. Đặng Thị G").category(kinh).build());
            courseRepository.save(Course.builder().name("Tiếng Anh Chuyên Ngành").credits(2)
                    .lecturer("ThS. Bùi Văn H").category(ngoaingu).build());
            courseRepository.save(Course.builder().name("Toán Cao Cấp").credits(4)
                    .lecturer("PGS. Đinh Thị I").category(khtn).build());
            courseRepository.save(Course.builder().name("Vật Lý Đại Cương").credits(3)
                    .lecturer("TS. Lý Văn J").category(khtn).build());
            courseRepository.save(Course.builder().name("Triết Học Mác-Lênin").credits(3)
                    .lecturer("GS. Mai Thị K").category(xhnh).build());

            System.out.println("=== Đã tạo dữ liệu mẫu thành công ===");
        }
    }
}
