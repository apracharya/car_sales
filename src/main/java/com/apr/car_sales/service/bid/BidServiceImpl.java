package com.apr.car_sales.service.bid;

import com.apr.car_sales.data.PaymentMethod;
import com.apr.car_sales.exception.MismatchException;
import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.bid.BidEntity;
import com.apr.car_sales.persistence.bid.BidRepository;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.car.CarRepository;
import com.apr.car_sales.persistence.purchase.PurchaseEntity;
import com.apr.car_sales.persistence.purchase.PurchaseRepository;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

// gonna have to change this to purchase instead
@Component
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    private final ModelMapper modelMapper;

    public BidServiceImpl(BidRepository bidRepository,
                          CarRepository carRepository,
                          UserRepository userRepository,
                          PurchaseRepository purchaseRepository,
                          ModelMapper modelMapper) {
        this.bidRepository = bidRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
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
    public List<BidModel> readAllBids() {
        return bidRepository.findAll()
                .stream()
                .map(bid -> modelMapper.map(bid, BidModel.class))
                .toList();
    }

    @Override
    public List<BidModel> readBidsForCar(int carId) {
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("car", "car id", carId));
        List<BidEntity> bids = car.getBids();
        return bids.stream()
                .map(bid -> modelMapper.map(bid, BidModel.class))
                .toList();
    }

    @Override
    public BidModel updateBid(BidModel bidModel, int bidId) {

        return null;
    }

    @Override
    public void deleteBid(int bidId) {
        bidRepository.deleteById(bidId);
    }

    @Override
    public BidModel askPrice(int bidId, double askPrice) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "bid id", bidId));

        bid.setAskPrice(askPrice);
        bidRepository.save(bid);
        return modelMapper.map(bid, BidModel.class);
    }

    @Override
    public BidModel acceptBySeller(int bidId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "bid id", bidId));
        bid.setAcceptBid(true);
        bidRepository.save(bid);

        double purchaseAmt = bid.getBidAmount();

        int carId = bid.getCar().getId();
        UserEntity bidder = bid.getBidder();
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "car id", carId));
        car.setBookedBy(bidder);
        car.setBooked(true);
        car.setBookedPrice(purchaseAmt);

        carRepository.save(car);

        PurchaseEntity purchase = new PurchaseEntity(car, bidder, purchaseAmt, PaymentMethod.BankDeposit, true, true, 4, "");
        purchaseRepository.save(purchase);

        return modelMapper.map(bid, BidModel.class);
    }

    // client accepts seller's askPrice
    @Override
    public BidModel acceptByClient(int bidId, int userId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "bid id", bidId));

        if(bid.getBidder().getId() != userId) {
            throw new MismatchException("user", "bid user");
        }

        bid.setAcceptBid(true); // seller accept bid
        bid.setBidAmount(bid.getAskPrice()); // client accept ask price.
        bidRepository.save(bid);

        int carId = bid.getCar().getId();
        UserEntity bidder = bid.getBidder();

        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "car id", carId));
        car.setBookedBy(bidder); // this did not work. why?
        car.setBookedPrice(bid.getAskPrice());
        car.setBooked(true);
        carRepository.save(car);

        return modelMapper.map(bid, BidModel.class);
    }

    @Override
    public BidModel rejectBid(int bidId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "bid id", bidId));
        bid.setAcceptBid(false);

        return modelMapper.map(bid, BidModel.class);
    }

    @Override
    public BidModel cancelPurchase(int bidId, int userId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "bid id", bidId));

        return modelMapper.map(bid, BidModel.class);
    }
}
