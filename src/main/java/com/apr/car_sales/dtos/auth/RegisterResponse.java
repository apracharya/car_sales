package com.apr.car_sales.dtos.auth;

import lombok.Data;

@Data
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
