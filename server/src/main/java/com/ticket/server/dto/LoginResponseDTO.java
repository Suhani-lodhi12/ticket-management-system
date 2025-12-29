package com.ticket.server.dto;

public class LoginResponseDTO {

    private String token;
    private String role;
    private String userName;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String role, String userName) {
        this.token = token;
        this.role = role;
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }
}
