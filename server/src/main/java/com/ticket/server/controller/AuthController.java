package com.ticket.server.controller;

import com.ticket.server.dto.LoginRequestDTO;
import com.ticket.server.dto.LoginResponseDTO;
import com.ticket.server.entity.User;
import com.ticket.server.security.JwtUtil;
import com.ticket.server.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {

        User user = userService.getUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

            System.out.println("DB Password = [" + user.getPassword() + "]");
System.out.println("Input Password = [" + loginRequest.getPassword() + "]");


        // TEMPORARY password check (plain text)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        //  Generate REAL JWT token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().getRoleName()
        );

        return new LoginResponseDTO(
                token,
                user.getRole().getRoleName(),
                user.getFullName()
        );
    }
}
