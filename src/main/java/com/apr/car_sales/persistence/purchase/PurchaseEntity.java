package com.apr.car_sales.persistence.purchase;

import com.apr.car_sales.data.PurchaseStatus;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user; // FIXME: change to buyer

    private double purchaseAmount;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    @OneToOne
    private PaymentEntity payment;

    private boolean isPaid;
    private boolean isDelivered;

    // yet to implement
    private double rating;
    private String review;

    public PurchaseEntity(CarEntity car,
                   UserEntity user,
                   double purchaseAmount,
                   PaymentEntity payment,
                   boolean isPaid,
                   boolean isDelivered,
                   double rating,
                   String review){

        this.car = car;
        this.user = user;
        this.purchaseAmount = purchaseAmount;
        this.payment = payment;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
        this.rating = rating;
        this.review = review;

    }
}
