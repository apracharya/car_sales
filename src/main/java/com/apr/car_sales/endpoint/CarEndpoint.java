package com.apr.car_sales.endpoint;

import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.photo.PhotoEntity;
import com.apr.car_sales.service.car.CarModel;
import com.apr.car_sales.service.car.CarService;
import com.apr.car_sales.service.photo.PhotoModel;
import com.apr.car_sales.service.photo.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/cars")
public class CarEndpoint {

    private final CarService carService;
    private final PhotoService photoService;
    private final ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;

    public CarEndpoint(CarService carService, PhotoService photoService, ModelMapper modelMapper) {
        this.carService = carService;
        this.photoService = photoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarModel> readCar(@PathVariable("id") int id) {
        CarModel car = carService.readCar(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<CarModel>> readAllCars(@RequestParam(value = "sortBy", defaultValue="id", required=false) String sortBy,
                                                      @RequestParam(value = "sortDir", defaultValue = "asc", required=false) String sortDir) {
        List<CarModel> car = carService.readAllCars(sortBy, sortDir);

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

    // files upload
    @PostMapping("/image/upload/{id}")
    public ResponseEntity<CarModel> uploadImages(@RequestParam("images") List<MultipartFile> images,
                                                  @PathVariable("id") int carId) throws IOException {
        CarModel carModel = carService.readCar(carId);
        List<String> fileNames = photoService.uploadImages(path, images);

        CarEntity carEntity = modelMapper.map(carModel, CarEntity.class);

        List<PhotoEntity> photos = new ArrayList<>();
        for (String fileName : fileNames) {
            PhotoEntity photo = new PhotoEntity();
            photo.setUrl(fileName);
            photo.setCar(carEntity);
            photos.add(photo);
        }
        List<PhotoModel> photoModels = photos.stream()
                .map(photo -> modelMapper.map(photo, PhotoModel.class))
                .toList();

        carModel.getPhotos().addAll(photoModels);
        CarModel updatedFilm = carService.updateCar(carModel, carId);

        return new ResponseEntity<>(updatedFilm, HttpStatus.OK);
    }

    @GetMapping("/image/{src}")
    public void downloadImage(@PathVariable("src") String imgName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = null;
        try {
            resource = photoService.getResource(path, imgName);
            String fileExtension = getFileExtension(imgName).toLowerCase();
            switch (fileExtension) {
                case "png":
                    response.setContentType(MediaType.IMAGE_PNG_VALUE);
                    break;

                case "jpg", "jpeg":
                    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                    break;
                case "gif":
                    response.setContentType(MediaType.IMAGE_GIF_VALUE);
                    break;

                default:
                    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                    break;

            }

            StreamUtils.copy(resource, response.getOutputStream());
            response.flushBuffer();
        } catch(FileNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            log.warn("File {}", imgName);
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    log.error("Error closing InputStream", e);
                }
            }
        }
    }

    private String getFileExtension(String imgName) {
        if (imgName == null || imgName.lastIndexOf('.') == -1) {
            return "";
        }
        return imgName.substring(imgName.lastIndexOf('.') + 1);
    }

}
