package com.apr.car_sales.service.bid;

import java.util.List;

public interface BidService {
    BidModel createBid(BidModel bidModel, int carId, int bidderId);
    BidModel readBid(int bidId);
    List<BidModel> readAllBids();
    List<BidModel> readBidsForCar(int carId);
    BidModel updateBid(BidModel bidModel, int bidId);
    void deleteBid(int bidId);

    BidModel askPrice(int bidId, double askPrice);

    BidModel acceptByClient(int bidId, int userId);
    BidModel acceptBySeller(int bidId);
    BidModel rejectBid(int bidId);

    BidModel cancelPurchase(int bidId, int userId);
}
