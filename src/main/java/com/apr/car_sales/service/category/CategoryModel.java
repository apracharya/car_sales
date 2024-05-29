package com.apr.car_sales.service.category;

import com.apr.car_sales.dtos.ad.AdDto;
import com.apr.car_sales.service.ad.AdModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryModel {
    private int id;
    private String type;
    private String description;
    private List<AdDto> ads;
}
