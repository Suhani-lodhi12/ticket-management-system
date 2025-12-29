package com.ticket.server.service;

import com.ticket.server.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(Role role);

    Optional<Role> getRoleById(Long roleId);

    Optional<Role> getRoleByName(String roleName);

    List<Role> getAllRoles();
}
