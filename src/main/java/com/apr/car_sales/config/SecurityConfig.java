package com.apr.car_sales.config;

import com.apr.car_sales.security.JwtAuthEntryPoint;
import com.apr.car_sales.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JwtAuthEntryPoint point;
    private JwtAuthFilter filter;

    public SecurityConfig(JwtAuthEntryPoint point, JwtAuthFilter filter) {
        this.point = point;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/cars/read/**").permitAll()
                        .requestMatchers("/cars/read").permitAll()
                        .requestMatchers("/users/create").permitAll()
                        .requestMatchers("/cars/image/**").permitAll()
                        .requestMatchers("/cars/image").permitAll()
                        .requestMatchers("/categories/read").permitAll()
                        .requestMatchers("/categories/read/**").permitAll()
                        .requestMatchers("/purchase/readAll").permitAll()
                        .requestMatchers("/chat/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
