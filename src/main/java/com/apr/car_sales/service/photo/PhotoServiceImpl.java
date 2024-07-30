package com.apr.car_sales.service.photo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PhotoServiceImpl implements PhotoService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // get file name
        String name = file.getOriginalFilename();

        // random name generation
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        // full path
        String filePath = path + File.separator + fileName;

        File f = new File(path);
        if( ! f.exists())
            f.mkdir();

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public List<String> uploadImages(String path, List<MultipartFile> files) throws IOException {
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
            // File name
            String name = file.getOriginalFilename();

            // random name generation
            String randomId = UUID.randomUUID().toString();
            String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

            // full path
            String filePath = path + File.separator + fileName1;

            // create folder if not exists
            File f = new File(path);
            if ( ! f.exists()) {
                f.mkdir();
            }

            // file copy
            Files.copy(file.getInputStream(), Paths.get(filePath));
            fileNames.add(fileName1);
        }

        return fileNames;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }


}
