package id.holigo.services.holigocouponservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.web.model.CouponDto;
import id.holigo.services.holigocouponservice.web.model.ListCouponDto;

@DecoratedWith(CouponMapperDecorator.class)
@Mapper
public interface CouponMapper {
    CouponDto couponToCouponDto(Coupon coupon);

    ListCouponDto couponToListCouponDto(Coupon coupon);
}
