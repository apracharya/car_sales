package com.apr.car_sales.repository;

import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.car.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CarRepositoryTests {

    @Autowired
    private CarRepository carRepository;

    private CarEntity createCar() {
        return CarEntity.builder()
                .brand("BMW")
                .model("M5")
                .colour("Black")
                .productionYear(2022)
                .horsePower(476)
                .torque(388)
                .topSpeed(279)
                .to60(3.33)
                .mileage(14.6)
                .gears(7)
                .seats(5)
                .bootSpace(294)
                .isNew(true)
                .description("Awesome car")
                .kilometers(0)
                .price(144999)
                .isStock(true)
                .isBooked(false)
                .build();
    }

    @Test
    void CarRepository_Save_ReturnNotNull() {
        CarEntity car = createCar();
        CarEntity saved = carRepository.save(car);
        Assertions.assertThat(saved).isNotNull();
    }

    @Test
    void CarRepository_FindById_ReturnCar() {
        CarEntity car = createCar();
        CarEntity saved = carRepository.save(car);

        CarEntity found = carRepository.findById(saved.getId()).get();

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getId()).isPositive();
        Assertions.assertThat(found.getId()).isEqualTo(saved.getId());
    }

    @Test
    void CarRepository_FindAll_ReturnMoreThanOne() {
        CarEntity car1 = createCar();
        CarEntity car2 = createCar();

        carRepository.save(car1);
        carRepository.save(car2);

        List<CarEntity> all = carRepository.findAll();

        Assertions.assertThat(all).isNotNull().isNotEmpty().hasSize(2);

    }

    @Test
    void CarRepository_UpdateCar_ReturnCarNotNull() {
        CarEntity car = createCar();
        CarEntity saved = carRepository.save(car);

        CarEntity found = carRepository.findById(saved.getId()).get();
        found.setBrand("Mercedes");
        found.setModel("AMG GT Black Series");
        CarEntity updated = carRepository.save(found);

        Assertions.assertThat(updated).isNotNull();
        Assertions.assertThat(updated.getBrand()).isEqualTo("Mercedes");
        Assertions.assertThat(updated.getModel()).isEqualTo("AMG GT Black Series");
    }

    @Test
    void CarRepository_DeleteCarById_ReturnCarIsEmpty() {
        CarEntity car = createCar();
        CarEntity saved = carRepository.save(car);

        carRepository.deleteById(saved.getId());
        Optional<CarEntity> deletedCar = carRepository.findById(saved.getId());
        Assertions.assertThat(deletedCar).isEmpty();
    }

    @Test
    void CarRepository_DeleteCar_ReturnCarIsEmpty() {
        CarEntity car = createCar();
        CarEntity saved = carRepository.save(car);

        CarEntity found = carRepository.findById(saved.getId()).get();

        carRepository.delete(found);

        Optional<CarEntity> deletedCar = carRepository.findById(saved.getId());
        Assertions.assertThat(deletedCar).isEmpty();
    }

}
