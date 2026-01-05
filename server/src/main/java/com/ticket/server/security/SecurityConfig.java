package com.ticket.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers("/api/auth/**").permitAll()

                        // CREATE TICKET → USER + ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/tickets")
                        .hasAnyRole("USER", "ADMIN")

                        // ADMIN → ALL TICKETS
                        .requestMatchers(HttpMethod.GET, "/api/tickets")
                        .hasRole("ADMIN")

                        // USER → MY TICKETS
                        .requestMatchers(HttpMethod.GET, "/api/tickets/my-created/**")
                        .hasRole("USER")

                        // ASSIGN → ADMIN
                        .requestMatchers("/api/tickets/*/assign/*")
                        .hasRole("ADMIN")

                        // STATUS → ADMIN + AGENT
                        .requestMatchers("/api/tickets/*/status/*")
                        .hasAnyRole("ADMIN", "AGENT")

                        // ALL OTHER TICKET APIs NEED AUTH
                        .requestMatchers("/api/tickets/**")
                        .authenticated()

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
