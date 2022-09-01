package id.holigo.services.holigocouponservice.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.common.model.TransactionDtoForUser;
import id.holigo.services.holigocouponservice.domain.Coupon;

public interface CouponRuleValidate {

    void validateTheCoupon(Coupon coupon, TransactionDtoForUser transactionDtoForUser) throws JsonProcessingException;

    Boolean isValid();

    String getMessage();
}
