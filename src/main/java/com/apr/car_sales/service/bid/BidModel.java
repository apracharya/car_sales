package com.apr.car_sales.service.bid;

import com.apr.car_sales.dtos.car.CarDto;
import com.apr.car_sales.dtos.user.UserDto;
import com.apr.car_sales.service.car.CarModel;
import com.apr.car_sales.service.user.UserModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BidModel {
    private int id;
    private double bidAmount;
    private double askPrice;
    private UserDto bidder;
    private CarDto car;
    private boolean acceptBid;
}
