package id.holigo.services.holigocouponservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import id.holigo.services.holigocouponservice.domain.CouponHtu;
import id.holigo.services.holigocouponservice.web.model.CouponHtuDto;

@DecoratedWith(CouponHtuMapperDecorator.class)
@Mapper
public interface CouponHtuMapper {
    CouponHtuDto couponHtuToCouponHtuDto(CouponHtu couponHtu);
}
