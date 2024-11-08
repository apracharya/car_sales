package com.apr.car_sales.endpoint;

import com.apr.car_sales.service.bid.BidModel;
import com.apr.car_sales.service.bid.BidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bids")
public class BidEndpoint {

    private final BidService bidService;

    public BidEndpoint(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping("/create/car/{carId}/bidder/{bidderId}")
    public ResponseEntity<BidModel> createBid(@RequestBody BidModel bidModel,
                                              @PathVariable int carId,
                                              @PathVariable int bidderId) {
        BidModel bid = bidService.createBid(bidModel, carId, bidderId);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @GetMapping("/read/{bidId}")
    public ResponseEntity<BidModel> readBid(@PathVariable int bidId) {
        BidModel bid = bidService.readBid(bidId);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    // accessible only by seller
    @PutMapping("/update/bid/{bidId}/ask/{askPrice}")
    public ResponseEntity<BidModel> updateAsk(@PathVariable int bidId,
                                              @PathVariable double askPrice) {
        BidModel askedPrice = bidService.askPrice(bidId, askPrice);
        return new ResponseEntity<>(askedPrice, HttpStatus.OK);
    }

    @PutMapping("/update/bid/{bidId}")
    public ResponseEntity<BidModel> updateBid(@RequestBody BidModel bidModel,
                                              @PathVariable int bidId) {
        BidModel updateBid = bidService.updateBid(bidModel, bidId);
        return new ResponseEntity<>(updateBid, HttpStatus.OK);
    }

    @PostMapping("/accept/buyer/{userId}/bid/{bidId}")
    public ResponseEntity<BidModel> acceptByClient(@PathVariable int bidId, @PathVariable int userId) {
        BidModel acceptedBid = bidService.acceptByClient(bidId, userId);
        return new ResponseEntity<>(acceptedBid, HttpStatus.OK);
    }

    @PostMapping("/acceptOriginal/car/{carId}/user/{userId}")
    public ResponseEntity<BidModel> acceptOriginalOffer(@PathVariable int carId,
                                                        @PathVariable int userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/accept/seller/{bidId}")
    public ResponseEntity<BidModel> acceptBySeller(@PathVariable int bidId) {
        BidModel acceptedBid = bidService.acceptBySeller(bidId);
        return new ResponseEntity<>(acceptedBid, HttpStatus.OK);
    }

}
