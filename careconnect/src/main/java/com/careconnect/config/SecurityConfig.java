
package com.careconnect.config;

import com.careconnect.repository.AuthTokenRepository;
import com.careconnect.security.TokenAuthenticationFilter;

import jakarta.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AuthTokenRepository authTokenRepository;

    public SecurityConfig(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        TokenAuthenticationFilter tokenAuthenticationFilter =
                new TokenAuthenticationFilter(
                        authTokenRepository
                );

        http
            // Disable CSRF for REST APIs
           
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
    


            .authorizeHttpRequests(auth -> auth

                .dispatcherTypeMatchers(
                    DispatcherType.ERROR
                ).permitAll()

                // Management Portal login and static files
                .requestMatchers(
                    "/login",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                // Public REST APIs
                .requestMatchers(
                    "/api/register",
                    "/api/login",
                    "/api/doctors",
                    "/api/doctors/**"
                ).permitAll()

                // All other REST APIs require authentication
                .requestMatchers(
                    "/api/**"
                ).authenticated()

                // Management Portal requires ADMIN
                .anyRequest().hasRole("ADMIN")
            )

            // Management Portal login
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
            )

            // Management Portal logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
            )

            // Add token authentication filter
            .addFilterBefore(
                tokenAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}

