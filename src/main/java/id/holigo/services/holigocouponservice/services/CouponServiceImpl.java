package id.holigo.services.holigocouponservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.common.TransactionDtoForUser;
import id.holigo.services.holigocouponservice.component.HotelCouponValidation;
import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.domain.CouponUser;
import id.holigo.services.holigocouponservice.repositories.CouponRepository;
import id.holigo.services.holigocouponservice.repositories.CouponUserRepository;
import id.holigo.services.holigocouponservice.services.transaction.TransactionService;
import id.holigo.services.holigocouponservice.web.model.ApplyCouponDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

    private HotelCouponValidation hotelCouponValidation;
    private TransactionService transactionService;

    private CouponRepository couponRepository;

    private CouponUserRepository couponUserRepository;

    private MessageSource messageSource;

    @Autowired
    public void setHotelCouponValidation(HotelCouponValidation hotelCouponValidation) {
        this.hotelCouponValidation = hotelCouponValidation;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setCouponUserRepository(CouponUserRepository couponUserRepository) {
        this.couponUserRepository = couponUserRepository;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public ApplyCouponDto getDiscountAmount(UUID transactionId, String couponCode, String paymentServiceId, Long userId) {

        BigDecimal discountValue = new BigDecimal(0);
        ApplyCouponDto applyCouponDto = ApplyCouponDto.builder().isValid(false).isFreeAdmin(false).isFreeServiceFee(false)
                .discountAmount(BigDecimal.valueOf(0.00)).build();
        Optional<Coupon> fetchCoupon = couponRepository.findByCode(couponCode);
        if (fetchCoupon.isEmpty()) {
            return applyCouponDto;
        }
        Coupon coupon = fetchCoupon.get();
        if (coupon.getQuantity() != null)
            if (coupon.getQuantity() <= 0) {
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.couponQuantityLimit", null, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }
        if (!coupon.getIsPublic()) {
            Optional<CouponUser> fetchCouponUser = couponUserRepository.findByUserIdAndCouponId(userId, coupon.getId());
            if (fetchCouponUser.isEmpty()) {
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.invalid", null, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }
            CouponUser couponUser = fetchCouponUser.get();
            if (couponUser.getQuantity() != null)
                if (couponUser.getQuantity() <= 0) {
                    applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.couponQuantityLimit", null, LocaleContextHolder.getLocale()));
                    return applyCouponDto;
                }
        }
        applyCouponDto.setIsFreeAdmin(coupon.getIsFreeAdmin());
        applyCouponDto.setIsFreeServiceFee(coupon.getIsFreeServiceFee());
        TransactionDtoForUser transactionDtoForUser = transactionService.getDetailTransaction(transactionId);
        if (coupon.getServiceId() != null)
            if (!transactionDtoForUser.getServiceId().equals(coupon.getServiceId())) {
                Object[] args = new Object[]{getService(coupon.getServiceId())};
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.serviceLimit", args, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }

        if (coupon.getProductId() != null)
            if (!transactionDtoForUser.getProductId().equals(coupon.getProductId())) {
                Object[] args = new Object[]{getService(coupon.getServiceId())};
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.serviceLimit", args, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }

        try {
            hotelCouponValidation.validateTheCoupon(coupon, transactionDtoForUser);
        } catch (JsonProcessingException e) {
            return applyCouponDto;
        }
        if (!hotelCouponValidation.isValid()) {
            applyCouponDto.setMessage(hotelCouponValidation.getMessage());
            return applyCouponDto;
        }

        if (coupon.getIsPercent() != null) {
            if (coupon.getIsPercent()) {
                discountValue = transactionDtoForUser.getFareAmount().multiply(coupon.getCouponValue());
            } else {
                discountValue = coupon.getCouponValue();
            }
        }
        applyCouponDto.setIsValid(true);
        applyCouponDto.setDiscountAmount(discountValue);
        return applyCouponDto;
    }

    private String getService(Integer serviceId) {

        String service = "";
        switch (serviceId) {
            case 28 -> service = "hotel";
            case 1 -> service = "Maskapai";
        }
        return service;

    }
}
