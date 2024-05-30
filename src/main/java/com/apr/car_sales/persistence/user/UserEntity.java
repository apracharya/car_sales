package com.apr.car_sales.persistence.user;

import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.bid.BidEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<CarEntity> cars = new ArrayList<>();

    @OneToMany(mappedBy = "bidder", cascade = CascadeType.ALL)
    private List<BidEntity> bids = new ArrayList<>();

    @OneToMany(mappedBy = "bookedBy", cascade = CascadeType.ALL)
    private List<CarEntity> bookedCars = new ArrayList<>();

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user", referencedColumnName = "username"),
//            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
//    )
//    private Set<RoleEntity> roles = new HashSet<>();
}
