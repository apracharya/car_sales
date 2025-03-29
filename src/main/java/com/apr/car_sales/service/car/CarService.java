package com.apr.car_sales.service.car;

import java.util.List;

public interface CarService {
    CarModel createCar(CarModel carModel, long categoryId, long sellerId);
    CarModel bookCar(long carId, long userId, double bookedPrice);
    CarModel cancelBooking(long carId, long userId);
    CarModel readCar(long id);
    List<CarModel> readAllCars(String sortBy, String sortDir);
    CarModel updateCar(CarModel carModel, long id);
    void deleteCar(long id);
}
