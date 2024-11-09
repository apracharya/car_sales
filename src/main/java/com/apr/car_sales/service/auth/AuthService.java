package com.apr.car_sales.service.auth;

public interface AuthService {
    JwtResponse login(JwtRequest request);
}
