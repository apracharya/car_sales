package com.apr.car_sales.endpoint;

import com.apr.car_sales.service.car.CarModel;
import com.apr.car_sales.service.car.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/cars")
public class CarEndpoint {

    private final CarService carService;

    public CarEndpoint(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarModel> readCar(@PathVariable("id") int id) {
        CarModel car = carService.readCar(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<CarModel>> readAllCars() {
        List<CarModel> car = carService.readAllCars();
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping("/create/category/{categoryId}/seller/{sellerId}")
    public ResponseEntity<CarModel> createCar(@RequestBody CarModel carModel,
                                              @PathVariable int categoryId,
                                              @PathVariable int sellerId) {
        CarModel created = carService.createCar(carModel, categoryId, sellerId);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<CarModel> bookCar(@RequestParam int car,
                                            @RequestParam int user,
                                            @RequestParam double price) {
        CarModel carModel = carService.bookCar(car, user, price);
        return new ResponseEntity<>(carModel, HttpStatus.OK);
    }

    @PostMapping("/book/cancel")
    public ResponseEntity<CarModel> cancelBook(@RequestParam int car,
                                               @RequestParam int user) {
        CarModel carModel = carService.cancelBooking(car, user);
        return new ResponseEntity<>(carModel, HttpStatus.OK);
    }

}
