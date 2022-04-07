package id.holigo.services.holigocouponservice.web.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.repositories.CouponRepository;
import id.holigo.services.holigocouponservice.web.mappers.CouponMapper;
import id.holigo.services.holigocouponservice.web.model.CouponDto;
import id.holigo.services.holigocouponservice.web.model.ListCouponDto;

@RestController
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @GetMapping("api/v1/coupons")
    public ResponseEntity<List<ListCouponDto>> getListCoupon() {
        List<Coupon> coupons = couponRepository.findAll();
        return new ResponseEntity<>(
                coupons.stream().map(couponMapper::couponToListCouponDto).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("api/v1/coupons/{id}")
    public ResponseEntity<CouponDto> getDetailCoupon(@PathVariable("id") UUID id) {
        Coupon coupon = couponRepository.getById(id);
        return new ResponseEntity<>(couponMapper.couponToCouponDto(coupon), HttpStatus.OK);
    }
}
