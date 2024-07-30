package com.apr.car_sales.persistence.car;

import com.apr.car_sales.persistence.bid.BidEntity;
import com.apr.car_sales.persistence.category.CategoryEntity;
import com.apr.car_sales.persistence.photo.PhotoEntity;
import com.apr.car_sales.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String brand;
    private String model;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<PhotoEntity> photos = new ArrayList<>();

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "ad_category",
//            joinColumns = @JoinColumn(name = "ad", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "category", referencedColumnName = "id")
//    )

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private int year;
    private String description;
    private double kilometers;
    private double price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<BidEntity> bids = new ArrayList<>();

//    private Set<UserEntity> bidders = new HashSet<>();

    private boolean isStock;
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "booked_id")
    private UserEntity bookedBy;

    private double bookedPrice;


}