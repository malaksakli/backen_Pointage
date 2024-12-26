package com.Blacher.Blacher.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Blacher.Blacher.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}