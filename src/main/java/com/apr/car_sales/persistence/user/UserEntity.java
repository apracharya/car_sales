package com.apr.car_sales.persistence.user;

import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.bid.BidEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<CarEntity> cars = new ArrayList<>();

    @OneToMany(mappedBy = "bidder", cascade = CascadeType.ALL)
    private List<BidEntity> bids = new ArrayList<>();

    @OneToMany(mappedBy = "bookedBy", cascade = CascadeType.ALL)
    private List<CarEntity> bookedCars = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user", referencedColumnName = "username"),
//            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
//    )
//    private Set<RoleEntity> roles = new HashSet<>();
}
