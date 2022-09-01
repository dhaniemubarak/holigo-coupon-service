package id.holigo.services.holigocouponservice.web.mappers;

import id.holigo.services.common.model.ApplyCouponDto;
import id.holigo.services.holigocouponservice.domain.ApplyCoupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ApplyCouponMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "coupon", ignore = true)
    ApplyCoupon applyCouponDtoToApplyCoupon(ApplyCouponDto applyCouponDto);

    @Mapping(target = "message", ignore = true)
    ApplyCouponDto applyCouponToApplyCouponDto(ApplyCoupon applyCoupon);
}
