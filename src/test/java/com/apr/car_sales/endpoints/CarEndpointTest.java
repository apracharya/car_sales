package com.apr.car_sales.endpoints;

import com.apr.car_sales.endpoint.CarEndpoint;
import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.car.CarRepository;
import com.apr.car_sales.service.car.CarModel;
import com.apr.car_sales.service.car.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarEndpointTest {
    @Mock
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarEndpoint carEndpoint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadCar() {
        // Arrange
        int carId = 1;
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);
        carEntity.setBrand("Toyota");
        carEntity.setModel("Corolla");
        carEntity.setColour("Red");

        CarModel carModel = new CarModel();
        carModel.setId(carId);
        carModel.setBrand("Toyota");
        carModel.setModel("Corolla");
        carModel.setColour("Red");

        when(carRepository.findById(carId)).thenReturn(java.util.Optional.of(carEntity));
        when(modelMapper.map(carEntity, CarModel.class)).thenReturn(carModel);
        when(carService.readCar(carId)).thenReturn(carModel);

        // Act
        ResponseEntity<CarModel> response = carEndpoint.readCar(carId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(carId, response.getBody().getId());
        assertEquals("Toyota", response.getBody().getBrand());
        assertEquals("Corolla", response.getBody().getModel());
        assertEquals("Red", response.getBody().getColour());

        verify(carService, times(1)).readCar(carId);
    }

    @Test
    void testReadCar_NotFound() {
        // Arrange
        int carId = 999;
        when(carService.readCar(carId)).thenThrow(new ResourceNotFoundException("Car", "car_id", carId));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            carEndpoint.readCar(carId);
        });

        verify(carService, times(1)).readCar(carId);
    }

}
