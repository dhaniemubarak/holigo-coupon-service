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

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

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
        if (couponHotelRuleDto.getCheckinPeriod() != null)
            if (couponHotelRuleDto.getCheckinPeriod().after(detailHotel.getCheckIn())) {
                Date date = new Date();
                date.setTime(couponHotelRuleDto.getCheckinPeriod().getTime());
                String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
                Object[] args = new Object[]{formattedDate};
                isValid = false;
                message = messageSource.getMessage("hotelCouponValidation.checkinPeriod", args, LocaleContextHolder.getLocale());
                return;
            }

        if (couponHotelRuleDto.getCheckoutPeriod() != null)
            if (couponHotelRuleDto.getCheckoutPeriod().before(detailHotel.getCheckOut())) {
                Date date = new Date();
                date.setTime(couponHotelRuleDto.getCheckoutPeriod().getTime());
                String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
                Object[] args = new Object[]{formattedDate};
                isValid = false;
                message = messageSource.getMessage("hotelCouponValidation.checkoutPeriod", args, LocaleContextHolder.getLocale());
                return;
            }

        if (couponHotelRuleDto.getMinimumPrice() != null)
            if (couponHotelRuleDto.getMinimumPrice().compareTo(transactionDtoForUser.getFareAmount()) > 0) {
                Object[] obj = new Object[]{NumberFormat.getNumberInstance().format(couponHotelRuleDto.getMinimumPrice())};
                isValid = false;
                message = messageSource.getMessage("hotelCouponValidation.minimumPrice", obj, LocaleContextHolder.getLocale());
                return;
            }

        if (couponHotelRuleDto.getMaximumPrice() != null)
            if (couponHotelRuleDto.getMaximumPrice().compareTo(transactionDtoForUser.getFareAmount()) < 0) {
                Object[] obj = new Object[]{NumberFormat.getNumberInstance().format(couponHotelRuleDto.getMaximumPrice())};
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
