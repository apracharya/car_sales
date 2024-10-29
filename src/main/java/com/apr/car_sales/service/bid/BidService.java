package com.apr.car_sales.service.bid;

public interface BidService {
    BidModel createBid(BidModel bidModel, int carId, int bidderId);
    BidModel readBid(int bidId);
    BidModel readAllBids();
    BidModel readBidsForCar(int carId);
    BidModel updateBid(BidModel bidModel, int bidId);
    void deleteBid(int bidId);

    BidModel askPrice(int bidId, double askPrice);

    BidModel acceptBid(int bidId);
    BidModel rejectBid(int bidId);
}
