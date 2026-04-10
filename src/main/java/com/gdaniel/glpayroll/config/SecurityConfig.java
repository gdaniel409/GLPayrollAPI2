package com.gdaniel.glpayroll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.gdaniel.glpayroll.jwt.filter.JwtRequestFilter;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

        private JwtRequestFilter jwtFilter;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                                .cors(Customizer.withDefaults())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**").permitAll()// Allow specific //
                                                                                            // endpoints
                                                .anyRequest().authenticated() // Secure all other requests
                                // .requestMatchers("/**").permitAll()// Allow specific endpoints
                                )
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless
                                                                                                        // sessions
                                );

                // Add the custom JWT filter BEFORE the standard authentication filter
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

}
