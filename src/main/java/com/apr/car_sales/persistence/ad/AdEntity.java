package com.apr.car_sales.persistence.ad;

import com.apr.car_sales.persistence.category.CategoryEntity;
import com.apr.car_sales.persistence.photo.PhotoEntity;
import com.apr.car_sales.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String brand;
    private String model;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "car_bid",
            joinColumns = @JoinColumn(name = "car", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bid", referencedColumnName = "id")
    )
    private Set<BidEntity> bids = new HashSet<>();

    private Set<UserEntity> bidders = new HashSet<>();

    private boolean isStock;
    private boolean isBooked;


}