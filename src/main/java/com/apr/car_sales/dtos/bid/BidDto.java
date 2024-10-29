package com.apr.car_sales.dtos.bid;

import com.apr.car_sales.dtos.car.CarDto;
import com.apr.car_sales.dtos.user.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BidDto {
    private int id;
    private double bidAmount;
    private double askPrice;
    private UserDto bidder;
}
