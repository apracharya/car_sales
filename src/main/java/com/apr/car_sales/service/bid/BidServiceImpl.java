package com.apr.car_sales.service.bid;

import com.apr.car_sales.data.BidStatus;
import com.apr.car_sales.data.RoleEnum;
import com.apr.car_sales.dtos.bid.BidDto;
import com.apr.car_sales.exception.MismatchException;
import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.bid.BidEntity;
import com.apr.car_sales.persistence.bid.BidRepository;
import com.apr.car_sales.persistence.car.CarEntity;
import com.apr.car_sales.persistence.car.CarRepository;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

// todo: change this to purchase if applicable
// todo: research if userId is needed in methods
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
    public BidModel createBid(BidModel bidModel, long carId, long bidderId) {
        UserEntity bidder = userRepository.findById(bidderId)
                .orElseThrow(() -> new ResourceNotFoundException("bidder", "id", bidderId));
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("car", "id", carId));
        BidEntity bid = modelMapper.map(bidModel, BidEntity.class);
        bid.setCar(car);
        bid.setBidder(bidder);
        BidEntity created = bidRepository.save(bid);
        return modelMapper.map(created, BidModel.class);
    }

    // only bidder and seller can read the bid
    @Override
    public BidModel readBid(long bidId, long userId) {
        BidEntity bid = bidRepository.findById(bidId).
                orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));
        if(bid.getCar().getSeller().getId() != userId && bid.getBidder().getId() != userId) {
            throw new MismatchException("buyer or seller");
        }

        return modelMapper.map(bid, BidModel.class);
    }


    // only seller can read this
    @Override
    public List<BidDto> readBidsForCar(long carId, long sellerId) {
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("car", "id", carId));

        validateUser(sellerId, car.getSeller().getId(), "seller");

        List<BidEntity> bids = car.getBids();
        return bids.stream()
                .map(bid -> modelMapper.map(bid, BidDto.class))
                .toList();
    }


    @Override
    public BidModel updateBid(BidModel bidModel, long bidId, long userId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        validateUser(userId, bid.getBidder().getId(), "bidder");

        bid.setBidAmount(bidModel.getBidAmount());
        return modelMapper.map(bid, BidModel.class);
    }

    @Override
    public void deleteBid(long bidId, long userId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        validateUser(userId, bid.getBidder().getId(), "bidder");
        bid.setBidStatus(BidStatus.CANCELLED);

        bidRepository.delete(bid);
    }


    @Override
    public BidModel counterOffer(long bidId, long sellerId, double askPrice) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        validateUser(sellerId, bid.getCar().getSeller().getId(), "seller");

        bid.setAskPrice(askPrice);
        bidRepository.save(bid);
        return modelMapper.map(bid, BidModel.class);
    }

    @Override
    public BidModel acceptBySeller(long bidId, long sellerId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        validateUser(sellerId, bid.getCar().getSeller().getId(), "seller");

        bid.setBidStatus(BidStatus.ACCEPTED);

        bidRepository.save(bid);

        double purchaseAmt = bid.getBidAmount();

        long carId = bid.getCar().getId();
        UserEntity bidder = bid.getBidder();
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("car", "id", carId));
        car.setBookedBy(bidder);
        car.setBooked(true);
        car.setBookedPrice(purchaseAmt);

        carRepository.save(car);

//        PurchaseEntity purchase = new PurchaseEntity(car, bidder, purchaseAmt, Payment.BankDeposit, true, true, 4, "");
//        purchaseRepository.save(purchase);

        return modelMapper.map(bid, BidModel.class);
    }

    // client accepts seller's counterOffer
    @Override
    public BidModel acceptByClient(long bidId, long userId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        validateUser(userId, bid.getBidder().getId(), "bidder");

        bid.setBidStatus(BidStatus.ACCEPTED); // seller accept bid
        bid.setBidAmount(bid.getAskPrice()); // client accept ask price.
        bidRepository.save(bid);

        long carId = bid.getCar().getId();
        UserEntity bidder = bid.getBidder();

        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("car", "id", carId));
        car.setBookedBy(bidder); // this did not work. why?
        car.setBookedPrice(bid.getAskPrice());
        car.setBooked(true);
        carRepository.save(car);

        return modelMapper.map(bid, BidModel.class);
    }

    // todo: preserve the history by maybe adding a cancel/rejected field in BidEntity
    @Override
    public BidModel rejectBid(long bidId, long sellerId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        validateUser(sellerId, bid.getCar().getSeller().getId(), "seller");

        bid.setBidStatus(BidStatus.REJECTED);

        return modelMapper.map(bid, BidModel.class);
    }

    // todo: add something to make the purchase thing happen and cancel preserving the record
    @Override
    public BidModel cancelPurchase(long bidId, long userId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        validateUser(userId, bid.getBidder().getId(), "buyer");

        return modelMapper.map(bid, BidModel.class);
    }

    public void validateUser(long expectedId, long actualId, String resource) throws MismatchException {
        if(actualId != expectedId) {
            throw new MismatchException(resource);
        }
    }

    /**
     * this might not work. My thought, instead of role table, we could use
     * a list of RoleEnum in user
    */
    public void validateSeller(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        boolean isSeller = user.getRoles().contains(RoleEnum.SELLER);
        if( ! isSeller) {
            throw new MismatchException("seller");
        }
    }
}
