package com.apr.car_sales.dtos.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private int id;
    private String type;
    private String description;
}