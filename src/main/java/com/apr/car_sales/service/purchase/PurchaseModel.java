package com.apr.car_sales.service.purchase;

import com.apr.car_sales.data.PurchaseStatus;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.purchase.PaymentEntity;
import com.apr.car_sales.persistence.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseModel {
    private long id;
    private CarEntity car;
    private UserEntity user;
    private double purchaseAmount;
    private PurchaseStatus purchaseStatus;
    private PaymentEntity payment;
    private boolean isPaid;
    private boolean isDelivered;
    private double rating;
    private String review;
}
