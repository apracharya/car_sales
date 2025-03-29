package com.apr.car_sales.dtos.car;

import com.apr.car_sales.data.EngineType;
import com.apr.car_sales.dtos.category.CategoryDto;
import com.apr.car_sales.service.photo.PhotoModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CarDto {
    private long id;
    private String brand;
    private String model;
    private String colour;
    private List<PhotoModel> photos = new ArrayList<>();
    private CategoryDto category;
    private int year;
    private int horsePower;
    private int torque;
    private double topSpeed;
    private double to60;
    private double mileage;
    private EngineType engineType;
    private int gears;
    private int seats;
    private double bootSpace;
    private boolean isNew;
    private String description;
    private double kilometers;
    private double price;
    private boolean isStock;
    private boolean isBooked;
}
