package id.holigo.services.holigocouponservice.web.controllers;

import id.holigo.services.holigocouponservice.services.CouponService;
import id.holigo.services.common.model.ApplyCouponDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ApplyCouponController {

    private CouponService couponService;

    @Autowired
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/api/v1/applyCoupon")
    public ResponseEntity<ApplyCouponDto> getDiscountAmount(@RequestParam("transactionId") UUID transactionId,
                                                            @RequestParam("couponCode") String couponCode,
                                                            @RequestParam(name = "paymentServiceId", required = false) String paymentServiceId,
                                                            @RequestHeader("user-id") Long userId) {
        return new ResponseEntity<>(couponService.getDiscountAmount(transactionId, couponCode, paymentServiceId, userId), HttpStatus.OK);
    }

    @PostMapping("/api/v1/applyCoupon")
    public ResponseEntity<ApplyCouponDto> createApplyCoupon(@RequestBody ApplyCouponDto applyCouponDto) {
        return new ResponseEntity<>(couponService.createApplyCoupon(applyCouponDto), HttpStatus.CREATED);
    }
}
