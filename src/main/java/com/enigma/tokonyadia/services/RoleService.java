package com.enigma.tokonyadia.services;

import com.enigma.tokonyadia.models.entity.Role;
import com.enigma.tokonyadia.utils.RoleEnum;

public interface RoleService {
    Role getOrSave(RoleEnum role);
}
