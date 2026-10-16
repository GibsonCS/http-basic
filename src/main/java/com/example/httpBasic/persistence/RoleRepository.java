package com.example.httpBasic.persistence;

import com.example.httpBasic.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
