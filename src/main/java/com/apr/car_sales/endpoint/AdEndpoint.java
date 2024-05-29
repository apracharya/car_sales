package com.apr.car_sales.endpoint;

import com.apr.car_sales.service.ad.AdModel;
import com.apr.car_sales.service.ad.AdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/ads")
public class AdEndpoint {

    private final AdService adService;

    public AdEndpoint(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<AdModel> readCarAd(@PathVariable("id") int id) {
        AdModel ad = adService.readCarAd(id);
        return new ResponseEntity<>(ad, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<AdModel>> readCarAd() {
        List<AdModel> ad = adService.readAllCarsAd();
        return new ResponseEntity<>(ad, HttpStatus.OK);
    }

    @PostMapping("/create/category/{categoryId}/seller/{sellerId}")
    public ResponseEntity<AdModel> createCarAd(@RequestBody AdModel adModel,
                                               @PathVariable int categoryId,
                                               @PathVariable int sellerId) {
        AdModel created = adService.createCarAd(adModel, categoryId, sellerId);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }


}
