package com.apr.car_sales.persistence.user;

import com.apr.car_sales.persistence.ad.AdEntity;
import com.apr.car_sales.persistence.ad.BidEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<AdEntity> ads = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_bids",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bid", referencedColumnName = "id")
    )
    private Set<BidEntity> bids = new HashSet<>();

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user", referencedColumnName = "username"),
//            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
//    )
//    private Set<RoleEntity> roles = new HashSet<>();
}
