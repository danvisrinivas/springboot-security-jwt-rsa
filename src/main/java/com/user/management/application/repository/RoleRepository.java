package com.user.management.application.repository;

import com.user.management.application.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByRoleName(String role);
}
