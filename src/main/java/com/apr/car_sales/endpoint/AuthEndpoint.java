package com.apr.car_sales.endpoint;

import com.apr.car_sales.service.auth.AuthService;
import com.apr.car_sales.service.auth.JwtRequest;
import com.apr.car_sales.service.auth.JwtResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth APIs")
public class AuthEndpoint {

    private final AuthService authService;

    public AuthEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        JwtResponse response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials invalid!";
    }
}
