package com.apr.car_sales.service.purchase;

import java.util.List;

public interface PurchaseService {
    PurchaseModel createPurchase(PurchaseModel purchaseModel, int bidId);
    PurchaseModel readPurchase(int purchaseId, int userId); // both seller and buyer can read this
    List<PurchaseModel> readAllPurchasesByUser(int buyerId);
    PurchaseModel cancelPurchase(int purchaseId, int buyerId);
}
