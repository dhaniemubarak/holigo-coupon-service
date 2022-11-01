package id.holigo.services.holigocouponservice.web.mappers;

import id.holigo.services.holigocouponservice.domain.CouponUser;
import id.holigo.services.common.model.CouponUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CouponUserMapper {
    @Mapping(target = "coupon", ignore = true)
    CouponUser couponUserDtoToCouponUser(CouponUserDto couponUserDto);
}
