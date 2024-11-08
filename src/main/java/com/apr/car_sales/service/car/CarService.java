package com.apr.car_sales.service.car;

import java.util.List;

public interface CarService {
    CarModel createCar(CarModel carModel, int categoryId, int sellerId);
    CarModel bookCar(int carId, int userId, double bookedPrice);
    CarModel cancelBooking(int carId, int userId);
    CarModel readCar(int id);
    List<CarModel> readAllCars(String sortBy, String sortDir);
    CarModel updateCar(CarModel carModel, int id);
    void deleteCar(int id);
}
