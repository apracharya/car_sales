package com.apr.car_sales.persistence.photo;

import com.apr.car_sales.persistence.ad.AdEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "url", length = 200)
//    @Column(columnDefinition="LONGTEXT")
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private AdEntity ad;

}
