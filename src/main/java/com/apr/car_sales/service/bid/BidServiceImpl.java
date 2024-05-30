package com.apr.car_sales.service.bid;

import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.bid.BidEntity;
import com.apr.car_sales.persistence.bid.BidRepository;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.car.CarRepository;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import com.apr.car_sales.service.user.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    private final CarRepository carRepository;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BidServiceImpl(BidRepository bidRepository,
                          CarRepository carRepository,
                          UserRepository userRepository,
                          ModelMapper modelMapper) {
        this.bidRepository = bidRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BidModel createBid(BidModel bidModel, int carId, int bidderId) {
        UserEntity bidder = userRepository.findById(bidderId)
                .orElseThrow(() -> new ResourceNotFoundException("Bidder", "user id", bidderId));
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "car id", carId));
        BidEntity bid = modelMapper.map(bidModel, BidEntity.class);
        bid.setCar(car);
        bid.setBidder(bidder);
        BidEntity created = bidRepository.save(bid);
        return modelMapper.map(created, BidModel.class);
    }

    @Override
    public BidModel readBid(int bidId) {
        BidEntity bid = bidRepository.findById(bidId).
                orElseThrow(() -> new ResourceNotFoundException("Bid", "bid id", bidId));
        return modelMapper.map(bid, BidModel.class);
    }

    @Override
    public BidModel readAllBids() {
        return null;
    }

    @Override
    public BidModel readBidsForCar(int carId) {
        return null;
    }

    @Override
    public BidModel updateBid(BidModel bidModel, int bidId) {
        return null;
    }

    @Override
    public void deleteBid(int bidId) {

    }
}
