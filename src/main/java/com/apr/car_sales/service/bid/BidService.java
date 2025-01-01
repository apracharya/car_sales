package com.apr.car_sales.service.bid;

import com.apr.car_sales.dtos.bid.AllBidsDto;
import com.apr.car_sales.dtos.bid.BidDto;

import java.util.List;

public interface BidService {
    BidModel createBid(BidModel bidModel, int carId, int bidderId);
    BidModel readBid(int bidId, int userId);
    List<BidDto> readBidsForCar(int carId, int sellerId);
    BidModel updateBid(BidModel bidModel, int bidId, int userId);
    void deleteBid(int bidId, int userId);

    BidModel counterOffer(int bidId, int sellerId, double askPrice);

    BidModel acceptByClient(int bidId, int userId);
    BidModel acceptBySeller(int bidId, int sellerId);
    BidModel rejectBid(int bidId, int sellerId);

    BidModel cancelPurchase(int bidId, int userId);
}
