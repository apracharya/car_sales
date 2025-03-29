package com.apr.car_sales.service.user;

import com.apr.car_sales.dtos.car.CarDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private List<CarDto> cars = new ArrayList<>();
    private List<CarDto> bookedCars = new ArrayList<>();

}
