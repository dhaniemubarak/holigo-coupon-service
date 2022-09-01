package id.holigo.services.holigocouponservice.services;

import id.holigo.services.common.model.ApplyCouponDto;

import java.util.UUID;

public interface CouponService {
    ApplyCouponDto getDiscountAmount(UUID transactionId, String couponCode, String paymentServiceId, Long userId);
}
