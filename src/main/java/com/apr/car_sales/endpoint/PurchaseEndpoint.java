package com.apr.car_sales.endpoint;

import com.apr.car_sales.service.purchase.PurchaseModel;
import com.apr.car_sales.service.purchase.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


// FIXME: everything related to purchase and payment!
@RestController
@RequestMapping("/api/purchase")
public class PurchaseEndpoint {
    private final PurchaseService purchaseService;

    public PurchaseEndpoint(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<PurchaseModel>> readAllByUserId(@RequestParam long userId) {
        List<PurchaseModel> purchases = purchaseService.readAllPurchasesByUser(userId);
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

}
