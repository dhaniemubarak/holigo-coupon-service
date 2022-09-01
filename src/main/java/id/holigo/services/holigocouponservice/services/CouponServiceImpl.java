package id.holigo.services.holigocouponservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.common.model.TransactionDtoForUser;
import id.holigo.services.common.model.UserDto;
import id.holigo.services.holigocouponservice.component.HotelCouponValidation;
import id.holigo.services.holigocouponservice.domain.ApplyCoupon;
import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.domain.CouponUser;
import id.holigo.services.holigocouponservice.repositories.ApplyCouponRepository;
import id.holigo.services.holigocouponservice.repositories.CouponRepository;
import id.holigo.services.holigocouponservice.repositories.CouponUserRepository;
import id.holigo.services.holigocouponservice.services.transaction.TransactionService;
import id.holigo.services.holigocouponservice.services.user.UserService;
import id.holigo.services.common.model.ApplyCouponDto;
import id.holigo.services.holigocouponservice.web.exceptions.CouponNotFoundException;
import id.holigo.services.holigocouponservice.web.mappers.ApplyCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponServiceImpl implements CouponService {

    private HotelCouponValidation hotelCouponValidation;

    private ApplyCouponRepository applyCouponRepository;

    private ApplyCouponMapper applyCouponMapper;
    private TransactionService transactionService;

    private CouponRepository couponRepository;

    private CouponUserRepository couponUserRepository;

    private MessageSource messageSource;

    private UserService userService;

    @Autowired
    public void setApplyCouponMapper(ApplyCouponMapper applyCouponMapper) {
        this.applyCouponMapper = applyCouponMapper;
    }

    @Autowired
    public void setApplyCouponRepository(ApplyCouponRepository applyCouponRepository) {
        this.applyCouponRepository = applyCouponRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        if (!coupon.getIsActive()) {
            applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.invalid", null, LocaleContextHolder.getLocale()));
            return applyCouponDto;
        }
        if (coupon.getQuantity() != null)
            if (coupon.getQuantity() <= 0) {
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.couponQuantityLimit", null, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        // valid at
        if (coupon.getValidAt() != null) {
            if (now.before(coupon.getValidAt())) {
                Date date = new Date();
                date.setTime(coupon.getValidAt().getTime());
                String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
                Object[] obj = new Object[]{formattedDate};
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.validLimit", obj, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }
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
            if (couponUser.getExpiredAt() != null)
                if (now.after(couponUser.getExpiredAt())) {
                    applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.couponTimeLimit", null, LocaleContextHolder.getLocale()));
                    return applyCouponDto;
                }
        } else {
            if (coupon.getExpiredAt() != null)
                if (now.after(coupon.getExpiredAt())) {
                    applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.couponTimeLimit", null, LocaleContextHolder.getLocale()));
                    return applyCouponDto;
                }
        }
        UserDto user = userService.getUser(userId);
        if (coupon.getUserGroup() != null)
            if (!coupon.getUserGroup().equals(user.getUserGroup())) {
                Object[] obj = new Object[]{coupon.getUserGroup()};
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.userGroupLimit", obj, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }

        if (coupon.getIsOfficialAccount())
            if (!user.getIsOfficialAccount()) {
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.officialAccountLimit", null, LocaleContextHolder.getLocale()));
                return applyCouponDto;
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

        if (coupon.getMinimumFare() != null)
            if (coupon.getMinimumFare().compareTo(transactionDtoForUser.getFareAmount()) > 0) {
                Object[] args = new Object[]{NumberFormat.getNumberInstance().format(coupon.getMinimumFare())};
                applyCouponDto.setMessage(messageSource.getMessage("applyCoupon.fareMinimumLimit", args, LocaleContextHolder.getLocale()));
                return applyCouponDto;
            }
        if (coupon.getRuleType() != null) {
            try {
                switch (coupon.getRuleType()) {
                    case "hotel" -> hotelCouponValidation.validateTheCoupon(coupon, transactionDtoForUser);
                }
                if (!hotelCouponValidation.isValid()) {
                    applyCouponDto.setMessage(hotelCouponValidation.getMessage());
                    return applyCouponDto;
                }
            } catch (JsonProcessingException e) {
                return applyCouponDto;
            }
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

    @Transactional
    @Override
    public ApplyCouponDto createApplyCoupon(ApplyCouponDto applyCouponDto) {
        ApplyCoupon applyCoupon = applyCouponMapper.applyCouponDtoToApplyCoupon(applyCouponDto);
        Optional<Coupon> fetchCoupon = couponRepository.findByCode(applyCoupon.getCouponCode());
        if (fetchCoupon.isEmpty()) {
            throw new CouponNotFoundException(messageSource.getMessage("applyCoupon.invalid", null, LocaleContextHolder.getLocale())
                    , null, false, false);
        }
        Coupon coupon = fetchCoupon.get();
        applyCoupon.setCoupon(coupon);
        if (!coupon.getIsPublic()) {
            Optional<CouponUser> fetchCouponUser = couponUserRepository.findByUserIdAndCouponId(applyCoupon.getUserId(), coupon.getId());
            if (fetchCouponUser.isEmpty()) {
                throw new CouponNotFoundException(messageSource.getMessage("applyCoupon.invalid", null, LocaleContextHolder.getLocale())
                        , null, false, false);
            }
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            CouponUser couponUser = fetchCouponUser.get();
            if (couponUser.getQuantity() != null)
                if (couponUser.getQuantity() <= 0) {
                    throw new CouponNotFoundException(messageSource.getMessage("applyCoupon.invalid", null, LocaleContextHolder.getLocale())
                            , null, false, false);
                }
            if (couponUser.getExpiredAt() != null)
                if (now.after(couponUser.getExpiredAt())) {
                    throw new CouponNotFoundException(messageSource.getMessage("applyCoupon.invalid", null, LocaleContextHolder.getLocale())
                            , null, false, false);
                }
            couponUser.setQuantity(couponUser.getQuantity() - 1);
            couponUserRepository.save(couponUser);
        } else {
            if (coupon.getQuantity() != null) {
                coupon.setQuantity(coupon.getQuantity() - 1);
                couponRepository.save(coupon);
            }
        }
        return applyCouponMapper.applyCouponToApplyCouponDto(applyCouponRepository.save(applyCoupon));
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
