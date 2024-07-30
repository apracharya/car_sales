package com.apr.car_sales.service.car;

import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.car.CarRepository;
import com.apr.car_sales.persistence.category.CategoryEntity;
import com.apr.car_sales.persistence.category.CategoryRepository;
import com.apr.car_sales.persistence.photo.PhotoEntity;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import com.apr.car_sales.service.user.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository,
                          CategoryRepository categoryRepository,
                          UserRepository userRepository,
                          ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarModel createCar(CarModel carModel, int categoryId, int sellerId) {

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        UserEntity seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", "user id", sellerId));

        CarEntity entity = modelMapper.map(carModel, CarEntity.class);
        entity.setCategory(category);
        entity.setSeller(seller);

        CarEntity created = carRepository.save(entity);
        return modelMapper.map(created, CarModel.class);
    }

    @Override
    public CarModel bookCar(int carId, int userId, double bookedPrice) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "car id", carId));
        car.setBooked(true);
        car.setBookedBy(user);
        car.setBookedPrice(bookedPrice);

        carRepository.save(car);

        return modelMapper.map(car, CarModel.class);


    }

    @Override
    public CarModel cancelBooking(int carId, int userId) {
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "car id", carId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        if(car.getBookedBy().equals(user)) {
            car.setBookedBy(null);
            car.setBooked(false);
            car.setBookedPrice(0);
            carRepository.save(car);
        } else {
            return null;
        }
        return modelMapper.map(car, CarModel.class);
    }


    @Override
    public CarModel readCar(int id) {
        CarEntity car = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", "car_id", id));
        return modelMapper.map(car, CarModel.class);
    }

    @Override
    public List<CarModel> readAllCars() {
        List<CarEntity> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> modelMapper.map(car, CarModel.class))
                .toList();
    }

    @Override
    public CarModel updateCar(CarModel carModel, int carId) {

        // convert carModel to Entity
        CarEntity carEntity = modelMapper.map(carModel, CarEntity.class);

        // Fetch existing film
        CarEntity existingCar = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "car id", carId));

        // Update film details
        existingCar.setBrand(carEntity.getBrand());
        existingCar.setModel(carEntity.getModel());
        existingCar.setColour(carEntity.getColour());
        existingCar.setYear(carEntity.getYear());
        existingCar.setDescription(carEntity.getDescription());
        existingCar.setKilometers(carEntity.getKilometers());
        existingCar.setPrice(carEntity.getPrice());


        // Update photos
        existingCar.getPhotos().clear();

        for (PhotoEntity photo : carEntity.getPhotos()) {
            photo.setCar(existingCar);
            existingCar.getPhotos().add(photo);
        }

        carRepository.save(existingCar);

        return modelMapper.map(existingCar, CarModel.class); // Or convert existingFilm to FilmModel if needed
    }


    @Override
    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}
