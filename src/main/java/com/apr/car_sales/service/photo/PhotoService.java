package com.apr.car_sales.service.photo;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PhotoService {
    String uploadImage(String path, MultipartFile file) throws IOException;
    InputStream getResource(String path, String fileName) throws FileNotFoundException;

    List<String> uploadImages(String path, List<MultipartFile> files) throws IOException;
}
