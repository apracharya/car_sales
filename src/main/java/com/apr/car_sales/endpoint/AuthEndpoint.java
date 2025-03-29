package com.apr.car_sales.endpoint;

import com.apr.car_sales.dtos.auth.RegisterRequest;
import com.apr.car_sales.dtos.auth.RegisterResponse;
import com.apr.car_sales.exception.MismatchException;
import com.apr.car_sales.service.auth.AuthService;
import com.apr.car_sales.service.auth.JwtRequest;
import com.apr.car_sales.service.auth.JwtResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth APIs")
public class AuthEndpoint {

    private final AuthService authService;

    public AuthEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login-header")
    public ResponseEntity<JwtResponse> loginHeader(@RequestBody JwtRequest request) {
        JwtResponse response = authService.loginHeader(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtRequest request, HttpServletResponse response) {
        try {
            authService.login(request, response);
            return new ResponseEntity<>("Login Successful", HttpStatus.OK);
        }  catch (Exception e) {
            throw new MismatchException("Error logging in");
        }
    }

}
