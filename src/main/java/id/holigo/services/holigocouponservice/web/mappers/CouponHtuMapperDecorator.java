package id.holigo.services.holigocouponservice.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import id.holigo.services.holigocouponservice.domain.CouponHtu;
import id.holigo.services.holigocouponservice.web.model.CouponHtuDto;

public abstract class CouponHtuMapperDecorator implements CouponHtuMapper {

    @Autowired
    private MessageSource messageSource;

    private CouponHtuMapper couponHtuMapper;

    @Autowired
    public void setCouponHtuMapper(CouponHtuMapper couponHtuMapper) {
        this.couponHtuMapper = couponHtuMapper;
    }

    @Override
    public CouponHtuDto couponHtuToCouponHtuDto(CouponHtu couponHtu) {
        CouponHtuDto couponHtuDto = couponHtuMapper.couponHtuToCouponHtuDto(couponHtu);
        couponHtuDto.setCaption(
                messageSource.getMessage(couponHtu.getIndexCaption(), null, LocaleContextHolder.getLocale()));
        return couponHtuDto;
    }
}
