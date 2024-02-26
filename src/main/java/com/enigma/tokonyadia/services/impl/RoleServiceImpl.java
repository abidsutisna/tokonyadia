package com.enigma.tokonyadia.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.models.entity.Role;
import com.enigma.tokonyadia.models.repos.RoleRepository;
import com.enigma.tokonyadia.services.RoleService;
import com.enigma.tokonyadia.utils.RoleEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(RoleEnum role) {
       Optional<Role> optionalRole = roleRepository.findByRole(role);
       if(optionalRole.isPresent()) {
           return optionalRole.get();
       }

       Role newRole = Role.builder().role(role).build();
       return roleRepository.save(newRole);
    }
}
