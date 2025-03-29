package com.apr.car_sales.dtos.bid;

import com.apr.car_sales.dtos.user.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BidDto {
    private long id;
    private double bidAmount;
    private double askPrice;
    private UserDto bidder;
}
