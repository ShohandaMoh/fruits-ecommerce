package com.project.fruits_ecommerce.repository;

import com.project.fruits_ecommerce.entities.Role;
import com.project.fruits_ecommerce.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
