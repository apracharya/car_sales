package com.apr.car_sales.dtos.bid;

import com.apr.car_sales.dtos.car.CarDto;
import com.apr.car_sales.dtos.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllBidsDto {
    private int id;
    private UserDto bidder;
    private CarDto car;
}
