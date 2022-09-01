package id.holigo.services.holigocouponservice.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.domain.CouponUser;
import id.holigo.services.holigocouponservice.repositories.CouponRepository;
import id.holigo.services.holigocouponservice.repositories.CouponUserRepository;
import id.holigo.services.holigocouponservice.web.mappers.CouponMapper;
import id.holigo.services.holigocouponservice.web.model.CouponDto;
import id.holigo.services.holigocouponservice.web.model.ListCouponDto;

@RestController
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponUserRepository couponUserRepository;

    @Autowired
    private CouponMapper couponMapper;

    List<Coupon> coupons;

    @GetMapping("api/v1/coupons")
    public ResponseEntity<List<ListCouponDto>> getListCoupon(
            @RequestParam(name = "isPublic", required = false) Boolean isPublic,
            @RequestHeader(name = "User-Id", required = false) Long userId,
            @RequestParam(name = "category", required = false) String category) {
        coupons = new ArrayList<>();
        List<Integer> serviceIds = new ArrayList<>();
        if (isPublic != null && isPublic) {
            if (category != null && !category.isEmpty()) {
                switch (category) {
                    case "airlines" -> {
                        serviceIds.add(1);
                        coupons = couponRepository.findAllByIsPublicAndIsShowAndServiceIdIn(true, true, serviceIds);
                    }
                    case "hotel" -> {
                        serviceIds.add(28);
                        coupons = couponRepository.findAllByIsPublicAndIsShowAndServiceIdIn(true, true, serviceIds);
                    }
                    case "bill" -> {
                        serviceIds.add(28);
                        coupons = couponRepository.findAllByIsPublicAndIsShowAndServiceIdNotIn(true, true,
                                serviceIds);
                    }
                    default -> coupons = couponRepository.findAllByIsPublicAndIsShow(true, true);
                }
            } else {
                coupons = couponRepository.findAllByIsPublicAndIsShow(true, true);
            }
        } else if (userId != null) {
            List<CouponUser> couponUsers = couponUserRepository.findAllByUserId(userId);
            if (couponUsers.size() > 0) {
                couponUsers.forEach(action -> {
                    if (category != null && !category.isEmpty()) {
                        switch (category) {
                            case "airlines":
                                if (action.getCoupon().getServiceId() == 1) {
                                    coupons.add(action.getCoupon());
                                }
                                break;
                            case "hotel":
                                if (action.getCoupon().getServiceId() == 28) {
                                    coupons.add(action.getCoupon());
                                }
                                break;
                            case "bill":
                                if (action.getCoupon().getServiceId() != 28 && action.getCoupon().getServiceId() != 1) {
                                    coupons.add(action.getCoupon());
                                }
                                break;
                            default:
                                coupons.add(action.getCoupon());
                                break;
                        }
                    } else {
                        coupons.add(action.getCoupon());
                    }
                });
            }
        }
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
