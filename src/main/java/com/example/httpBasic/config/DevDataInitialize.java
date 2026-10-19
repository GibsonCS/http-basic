package com.example.httpBasic.config;

import com.example.httpBasic.entity.Role;
import com.example.httpBasic.entity.User;
import com.example.httpBasic.persistence.RoleRepository;
import com.example.httpBasic.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataInitialize implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.security.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {

        String userName = "gibson@example.com";
        String adminUserName = "admin@example.com";

        String ROLE_USER = "USER";
        String ROLE_ADMIN = "ADMIN";

        if (userRepository.findByEmail(userName).isPresent()) {
            throw new RuntimeException("usuário já existe");
        }

        if (userRepository.findByEmail(adminUserName).isPresent()) {
            throw new RuntimeException("usuário admin já existe");
        }

        if (roleRepository.findByName(ROLE_USER).isPresent()) {
            throw new RuntimeException("role user já existe");
        }

        if (roleRepository.findByName(ROLE_ADMIN).isPresent()) {
            throw new RuntimeException("role admin já existe");
        }

        User user = new User();
        user.setEmail(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDate.now());

        Role role = new Role();
        role.setName(ROLE_USER);

        user.getRoles().add(role);

        User admin = new User();
        admin.setEmail(adminUserName);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setCreatedAt(LocalDate.now());

        Role roleAdmin = new Role();
        roleAdmin.setName(ROLE_ADMIN);

        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(role);

        roleRepository.save(role);
        roleRepository.save(roleAdmin);

        userRepository.save(user);
        userRepository.save(admin);
    }
}
