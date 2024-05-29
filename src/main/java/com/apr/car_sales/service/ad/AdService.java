package com.apr.car_sales.service.ad;

import java.util.List;

public interface AdService {
    AdModel createCarAd(AdModel adModel, int categoryId, int sellerId);
    AdModel readCarAd(int id);
    List<AdModel> readAllCarsAd();
    AdModel updateCarAd(AdModel carModel, int id);
    void deleteCarAd(int id);
}
