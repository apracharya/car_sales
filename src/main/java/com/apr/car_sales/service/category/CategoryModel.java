package com.apr.car_sales.service.category;

import com.apr.car_sales.dtos.car.CarDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryModel {
    private long id;
    private String type;
    private String description;
    private List<CarDto> cars;
}
