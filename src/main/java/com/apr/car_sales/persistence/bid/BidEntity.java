package com.apr.car_sales.persistence.bid;

import com.apr.car_sales.data.BidStatus;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bids")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double bidAmount;
    private double askPrice;

    @ManyToOne
    private UserEntity bidder;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;
}
