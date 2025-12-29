package com.ticket.server.controller;

import com.ticket.server.entity.Role;
import com.ticket.server.entity.User;
import com.ticket.server.service.RoleService;
import com.ticket.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // Create User (Employee / Agent)
    @PostMapping
    public User createUser(@RequestBody User user) {

        // Default role = USER
        Role role = roleService.getRoleByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        user.setRole(role);
        return userService.createUser(user);
    }

    // Get all users (Admin use)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
