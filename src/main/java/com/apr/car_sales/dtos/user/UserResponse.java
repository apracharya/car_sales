package com.apr.car_sales.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
}
