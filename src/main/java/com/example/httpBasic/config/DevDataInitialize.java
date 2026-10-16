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


        User user = new User();
        user.setEmail("gibson@example.com");
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDate.now());

        Role role = new Role();
        role.setName("USER");

        roleRepository.save(role);

        user.getRoles().add(role);

        userRepository.save(user);
    }
}
