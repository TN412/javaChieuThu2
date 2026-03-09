package com.example.buoi5.config;

import com.example.buoi5.model.Account;
import com.example.buoi5.model.Role;
import com.example.buoi5.repository.AccountRepository;
import com.example.buoi5.repository.RoleRepository;
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
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Tạo 2 role nếu chưa tồn tại
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

        // Tạo account admin nếu chưa tồn tại
        if (accountRepository.findByUsername("admin").isEmpty()) {
            Account admin = Account.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(new HashSet<>(Set.of(adminRole)))
                    .build();
            accountRepository.save(admin);
            System.out.println("==> Da tao tai khoan ADMIN  | username: admin    | password: admin123");
        }

        // Tạo account user nếu chưa tồn tại
        if (accountRepository.findByUsername("user").isEmpty()) {
            Account user = Account.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .roles(new HashSet<>(Set.of(userRole)))
                    .build();
            accountRepository.save(user);
            System.out.println("==> Da tao tai khoan USER   | username: user     | password: user123");
        }
    }
}
