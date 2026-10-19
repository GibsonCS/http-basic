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
import java.util.Set;

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

        Role ROLE_USER = findOrCreateRole("USER");
        Role ROLE_ADMIN = findOrCreateRole("ADMIN");

        findOrCreateUser(userName, password, Set.of(ROLE_USER));
        findOrCreateUser(adminUserName, password, Set.of(ROLE_ADMIN, ROLE_USER));
    }

    private void findOrCreateUser(String email, String password, Set<Role> roles) {
        userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(roles);
            user.setCreatedAt(LocalDate.now());

            return userRepository.save(user);
        });
    }

    private Role findOrCreateRole(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        });
    }
}
