package com.apr.car_sales.service.bid;

import com.apr.car_sales.dtos.bid.BidDto;

import java.util.List;

public interface BidService {
    BidModel createBid(BidModel bidModel, long carId, long bidderId);
    BidModel readBid(long bidId, long userId);
    List<BidDto> readBidsForCar(long carId, long sellerId);
    BidModel updateBid(BidModel bidModel, long bidId, long userId);
    void deleteBid(long bidId, long userId);

    BidModel counterOffer(long bidId, long sellerId, double askPrice);

    BidModel acceptByClient(long bidId, long userId);
    BidModel acceptBySeller(long bidId, long sellerId);
    BidModel rejectBid(long bidId, long sellerId);

    BidModel cancelPurchase(long bidId, long userId);
}
