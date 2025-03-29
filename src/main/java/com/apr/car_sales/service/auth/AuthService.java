package com.apr.car_sales.service.auth;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    JwtResponse loginHeader(JwtRequest request);
    void login(JwtRequest request, HttpServletResponse response);
}
