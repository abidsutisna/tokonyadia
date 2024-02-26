package com.enigma.tokonyadia.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.tokonyadia.models.entity.Role;
import java.util.Optional;

import com.enigma.tokonyadia.utils.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
    Optional<Role> findByRole(RoleEnum role);
}
