package id.holigo.services.holigocouponservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.web.model.CouponDto;
import id.holigo.services.holigocouponservice.web.model.ListCouponDto;
import org.mapstruct.Mapping;

@DecoratedWith(CouponMapperDecorator.class)
@Mapper
public interface CouponMapper {
    @Mapping(target = "shortTerms", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "label", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "category", ignore = true)
    CouponDto couponToCouponDto(Coupon coupon);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "category", ignore = true)
    ListCouponDto couponToListCouponDto(Coupon coupon);
}
