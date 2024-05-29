package com.apr.car_sales.service.user;

import com.apr.car_sales.dtos.ad.AdDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private List<AdDto> ads = new ArrayList<>();
}
