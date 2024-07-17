package com.example.miraclefield.repository;

import com.example.miraclefield.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Override
    void delete(Role role);
}