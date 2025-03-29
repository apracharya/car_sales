package com.apr.car_sales.service.purchase;

import java.util.List;

public interface PurchaseService {
    PurchaseModel createPurchase(PurchaseModel purchaseModel, long bidId);
    PurchaseModel readPurchase(long purchaseId, long userId); // both seller and buyer can read this
    List<PurchaseModel> readAllPurchasesByUser(long buyerId);
    PurchaseModel cancelPurchase(long purchaseId, long buyerId);
}
