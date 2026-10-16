package com.example.httpBasic.security;

import com.example.httpBasic.entity.User;
import com.example.httpBasic.persistence.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> foundedUser = userRepository.findByEmail(username);

        if (foundedUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return new CustomUserDetails(foundedUser.get());
    }
}
