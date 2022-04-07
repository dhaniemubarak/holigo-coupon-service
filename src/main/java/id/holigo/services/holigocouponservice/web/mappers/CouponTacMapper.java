package id.holigo.services.holigocouponservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import id.holigo.services.holigocouponservice.domain.CouponTac;
import id.holigo.services.holigocouponservice.web.model.CouponTacDto;

@DecoratedWith(CouponTacMapperDecorator.class)
@Mapper
public interface CouponTacMapper {
    CouponTacDto couponTacToCouponTacDto(CouponTac couponTac);
}
