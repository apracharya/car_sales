package com.apr.car_sales.persistence.ad;

import com.apr.car_sales.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bids")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidEntity {
    @Id
    private int id;
    private double bidAmount;

    @ManyToOne
    private UserEntity bidder;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private AdEntity car;
}
