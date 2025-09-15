package com.tournoipro.config;

import com.tournoipro.model.Role;
import com.tournoipro.model.User;
import com.tournoipro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        if (!userRepository.existsByUsername("admin")) {
            // Create default admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@tournoipro.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            
            userRepository.save(admin);
            System.out.println("✅ Default admin user created:");
            System.out.println("   Username: admin");
            System.out.println("   Password: admin123");
            System.out.println("   Email: admin@tournoipro.com");
        }

        // Check if test user already exists
        if (!userRepository.existsByUsername("user")) {
            // Create default test user
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@tournoipro.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.USER);
            user.setEnabled(true);
            
            userRepository.save(user);
            System.out.println("✅ Default test user created:");
            System.out.println("   Username: user");
            System.out.println("   Password: user123");
            System.out.println("   Email: user@tournoipro.com");
        }
    }
}