package id.holigo.services.holigocouponservice.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.CouponHotelRuleDto;
import id.holigo.services.common.DetailHotelDto;
import id.holigo.services.common.TransactionDtoForUser;
import id.holigo.services.holigocouponservice.domain.Coupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelCouponValidation implements CouponRuleValidate {

    private final ObjectMapper objectMapper;

    private final MessageSource messageSource;

    private Boolean isValid;

    private String message;

    @Override
    public void validateTheCoupon(Coupon coupon, TransactionDtoForUser transactionDtoForUser) throws JsonProcessingException {

        isValid = true;
        DetailHotelDto detailHotel = objectMapper.convertValue(transactionDtoForUser.getDetail(), DetailHotelDto.class);
        CouponHotelRuleDto couponHotelRuleDto = objectMapper.readValue(coupon.getRuleValue(), CouponHotelRuleDto.class);
        log.info("Hotel rating -> {}", detailHotel.getHotel().getRating());
        log.info("Coupon hotel rating -> {}", couponHotelRuleDto.getMaximumStarRating());
        if (couponHotelRuleDto.getMinimumStarRating() != null)
            if (couponHotelRuleDto.getMinimumStarRating() > detailHotel.getHotel().getRating()) {
                Object[] obj = new Object[]{couponHotelRuleDto.getMinimumStarRating()};
                isValid = false;
                message = messageSource.getMessage("hotelCouponValidation.minimumStarRating", obj, LocaleContextHolder.getLocale());
                return;
            }

        if (couponHotelRuleDto.getMaximumStarRating() != null)
            if (couponHotelRuleDto.getMaximumStarRating() < detailHotel.getHotel().getRating()) {
                Object[] obj = new Object[]{couponHotelRuleDto.getMaximumStarRating()};
                isValid = false;
                message = messageSource.getMessage("hotelCouponValidation.maximumStarRating", obj, LocaleContextHolder.getLocale());
                return;
            }

        if (couponHotelRuleDto.getMinimumPrice() != null)
            if (couponHotelRuleDto.getMinimumPrice().compareTo(transactionDtoForUser.getFareAmount()) > 0) {
                Object[] obj = new Object[]{NumberFormat.getCurrencyInstance().format(couponHotelRuleDto.getMinimumPrice())};
                isValid = false;
                message = messageSource.getMessage("hotelCouponValidation.minimumPrice", obj, LocaleContextHolder.getLocale());
                return;
            }

        if (couponHotelRuleDto.getMaximumPrice() != null)
            if (couponHotelRuleDto.getMaximumPrice().compareTo(transactionDtoForUser.getFareAmount()) < 0) {
                Object[] obj = new Object[]{NumberFormat.getCurrencyInstance().format(couponHotelRuleDto.getMaximumPrice())};
                isValid = false;
                message = messageSource.getMessage("hotelCouponValidation.maximumPrice", obj, LocaleContextHolder.getLocale());
            }
    }

    @Override
    public Boolean isValid() {
        return isValid;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
