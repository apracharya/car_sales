package com.apr.car_sales.persistence.photo;

import com.apr.car_sales.persistence.car.CarEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "url", length = 200)
//    @Column(columnDefinition="LONGTEXT")
    private String url;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity car;

}
