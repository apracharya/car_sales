package com.apr.car_sales.service.auth;

import com.apr.car_sales.dtos.auth.RegisterRequest;
import com.apr.car_sales.dtos.auth.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    JwtResponse loginHeader(JwtRequest request);
    void login(JwtRequest request, HttpServletResponse response);

    RegisterResponse register(RegisterRequest request);
}
