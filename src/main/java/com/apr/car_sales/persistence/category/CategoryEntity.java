package com.apr.car_sales.persistence.category;

import com.apr.car_sales.persistence.car.CarEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private String description;
//    private String longDescription;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<CarEntity> cars = new HashSet<>();
}
