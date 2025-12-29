package com.ticket.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            // VERY IMPORTANT (CORS ENABLE)
            .cors(cors -> {})

            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                // LOGIN FREE
                .requestMatchers("/api/auth/**").permitAll()

                // ADMIN only
                .requestMatchers("/api/tickets/*/assign/*").hasRole("ADMIN")

                // ADMIN + AGENT
                .requestMatchers("/api/tickets/*/status/*")
                .hasAnyRole("ADMIN", "AGENT")

                // AUTH REQUIRED
                .requestMatchers("/api/tickets/**", "/api/categories/**")
                .authenticated()

                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
