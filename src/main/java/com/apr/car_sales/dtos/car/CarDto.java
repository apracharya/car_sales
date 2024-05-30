package com.apr.car_sales.dtos.car;

import com.apr.car_sales.dtos.category.CategoryDto;
import com.apr.car_sales.service.photo.PhotoModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CarDto {
    private int id;
    private String brand;
    private String model;
    private List<PhotoModel> photos = new ArrayList<>();
    private CategoryDto category;
    private int year;
    private String description;
    private double kilometers;
    private double price;
    private boolean isStock;
    private boolean isBooked;
}
