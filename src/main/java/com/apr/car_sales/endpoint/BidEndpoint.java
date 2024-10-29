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

    @PutMapping("/update/bid/{bidId}/ask/{askPrice}")
    public ResponseEntity<BidModel> updateAsk(@RequestBody BidModel bidModel,
                                              @PathVariable int bidId,
                                              @PathVariable double askPrice) {
        return new ResponseEntity<>(bidModel, HttpStatus.OK);
    }

    @PutMapping("/update/bid/{bidId}")
    public ResponseEntity<BidModel> updateBid(@RequestBody BidModel bidModel,
                                              @PathVariable int bidId) {
        BidModel updateBid = bidService.updateBid(bidModel, bidId);
        return new ResponseEntity<>(updateBid, HttpStatus.OK);
    }



}
