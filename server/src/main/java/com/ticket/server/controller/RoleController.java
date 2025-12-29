package com.ticket.server.controller;

import com.ticket.server.entity.Role;
import com.ticket.server.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Create Role
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    // Get All Roles
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
