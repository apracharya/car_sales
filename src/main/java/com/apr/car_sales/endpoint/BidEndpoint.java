package com.apr.car_sales.endpoint;

import com.apr.car_sales.dtos.bid.BidDto;
import com.apr.car_sales.response.ApiResponse;
import com.apr.car_sales.service.bid.BidModel;
import com.apr.car_sales.service.bid.BidService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo: api testing has not been done after necessary user validation
@RestController
@RequestMapping("/api/bids")
@Tag(name = "Bid APIs")
public class BidEndpoint {

    private final BidService bidService;

    public BidEndpoint(BidService bidService) {
        this.bidService = bidService;
    }

    // create
    @PostMapping("/create")
    public ResponseEntity<BidModel> createBid(@RequestBody BidModel bidModel,
                                              @RequestParam int carId,
                                              @RequestParam int bidderId) {
        BidModel bid = bidService.createBid(bidModel, carId, bidderId);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    // read -> only by seller and bidder
    @GetMapping("/read")
    public ResponseEntity<BidModel> readBid(@RequestParam int bidId, @RequestParam int userId) {
        BidModel bid = bidService.readBid(bidId, userId);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @GetMapping("/readByCar")
    public ResponseEntity<List<BidDto>> readBidsForCar(@RequestParam int carId,
                                                         @RequestParam int sellerId) {
        List<BidDto> bids = bidService.readBidsForCar(carId, sellerId);
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }


    @PutMapping("/seller/counter")
    public ResponseEntity<BidModel> counterOffer(@RequestParam int bidId,
                                                 @RequestParam int sellerId,
                                                 @RequestParam double askPrice) {
        BidModel askedPrice = bidService.counterOffer(bidId, sellerId, askPrice);
        return new ResponseEntity<>(askedPrice, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<BidModel> updateBid(@RequestBody BidModel bidModel,
                                              @RequestParam int bidId,
                                              @RequestParam int userId) {
        BidModel updateBid = bidService.updateBid(bidModel, bidId, userId);
        return new ResponseEntity<>(updateBid, HttpStatus.OK);
    }

    @PostMapping("/buyer/accept")
    public ResponseEntity<BidModel> acceptByClient(@RequestParam int bidId, @RequestParam int userId) {
        BidModel acceptedBid = bidService.acceptByClient(bidId, userId);
        return new ResponseEntity<>(acceptedBid, HttpStatus.OK);
    }

    // not sure why this is here!
    @PostMapping("/acceptOriginal")
    public ResponseEntity<BidModel> acceptOriginalOffer(@RequestParam int carId,
                                                        @RequestParam int userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // todo: add accessibility for only seller roles when roles are implemented
    @PostMapping("/seller/accept")
    public ResponseEntity<BidModel> acceptBySeller(@RequestParam int bidId,
                                                   @RequestParam int sellerId) {
        BidModel acceptedBid = bidService.acceptBySeller(bidId, sellerId);
        return new ResponseEntity<>(acceptedBid, HttpStatus.OK);
    }

    // bids/delete?bidId=1&userId=2
    @DeleteMapping("/delete")
    public ApiResponse deleteBid(@RequestParam int bidId, @RequestParam int userId) {
        bidService.deleteBid(bidId, userId);
        return new ApiResponse("Bid deleted successfully!", true);
    }

    @PostMapping("/seller/reject")
    public ResponseEntity<BidModel> rejectBid(@RequestParam int sellerId,
                                              @RequestParam int bidId) {
        BidModel bid = bidService.rejectBid(bidId, sellerId);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

}
