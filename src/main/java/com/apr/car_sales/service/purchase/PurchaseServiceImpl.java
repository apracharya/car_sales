package com.apr.car_sales.service.purchase;

import com.apr.car_sales.data.PurchaseStatus;
import com.apr.car_sales.exception.MismatchException;
import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.bid.BidEntity;
import com.apr.car_sales.persistence.bid.BidRepository;
import com.apr.car_sales.persistence.car.CarRepository;
import com.apr.car_sales.persistence.purchase.PurchaseEntity;
import com.apr.car_sales.persistence.purchase.PurchaseRepository;
import com.apr.car_sales.persistence.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BidRepository bidRepository;
    private final CarRepository carRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ModelMapper modelMapper, UserRepository userRepository, BidRepository bidRepository, CarRepository carRepository) {
        this.purchaseRepository = purchaseRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bidRepository = bidRepository;
        this.carRepository = carRepository;
    }

    @Override
    public PurchaseModel createPurchase(PurchaseModel purchaseModel, long bidId) {
        BidEntity bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("bid", "id", bidId));

        if(purchaseModel.getUser().getId() != bid.getBidder().getId())
            throw new MismatchException("buyer");

        PurchaseEntity purchase = modelMapper.map(purchaseModel, PurchaseEntity.class);
        PurchaseEntity saved = purchaseRepository.save(purchase);
        return modelMapper.map(saved, PurchaseModel.class);
    }

    @Override
    public PurchaseModel readPurchase(long purchaseId, long userId) {
        PurchaseEntity purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("purchase", "id", purchaseId));
        if(purchase.getUser().getId() != userId)
            throw new MismatchException("buyer");

        return modelMapper.map(purchase, PurchaseModel.class);
    }

    // this does not look secure!
    @Override
    public List<PurchaseModel> readAllPurchasesByUser(long buyerId) {


        // when null this ain't throwing exception!!
        List<PurchaseEntity> purchases = purchaseRepository.findAllByUserId(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("purchase by user", "id", buyerId));

        return purchases.stream()
                .map(x -> modelMapper.map(x, PurchaseModel.class))
                .toList();
    }

    @Override
    public PurchaseModel cancelPurchase(long purchaseId, long buyerId) {
        PurchaseEntity purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("purchase", "id", purchaseId));

        if(purchase.getUser().getId() != buyerId)
            throw new MismatchException("buyer");

        purchase.setPurchaseStatus(PurchaseStatus.CANCELLED);
        return modelMapper.map(purchase, PurchaseModel.class);
    }

//    amar rai
//    dhungedhara 16
//    achyut

}
