package id.holigo.services.holigocouponservice.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import id.holigo.services.holigocouponservice.domain.CouponTac;
import id.holigo.services.holigocouponservice.web.model.CouponTacDto;

public abstract class CouponTacMapperDecorator implements CouponTacMapper {

    @Autowired
    private MessageSource messageSource;

    private CouponTacMapper couponTacMapper;

    @Autowired
    public void setCouponTacMapper(CouponTacMapper couponTacMapper) {
        this.couponTacMapper = couponTacMapper;
    }

    @Override
    public CouponTacDto couponTacToCouponTacDto(CouponTac couponTac) {
        CouponTacDto couponTacDto = couponTacMapper.couponTacToCouponTacDto(couponTac);
        couponTacDto.setCaption(
                messageSource.getMessage(couponTac.getIndexCaption(), null, LocaleContextHolder.getLocale()));
        return couponTacDto;
    }
}
