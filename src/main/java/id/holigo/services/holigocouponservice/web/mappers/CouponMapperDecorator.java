package id.holigo.services.holigocouponservice.web.mappers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.web.model.CouponDto;
import id.holigo.services.holigocouponservice.web.model.ListCouponDto;

public abstract class CouponMapperDecorator implements CouponMapper {

    @Autowired
    private MessageSource messageSource;

    private CouponMapper couponMapper;

    private CouponHtuMapper couponHtuMapper;

    private CouponTacMapper couponTacMapper;

    @Autowired
    public void setCouponMapper(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    @Autowired
    public void setCouponHtuMapper(CouponHtuMapper couponHtuMapper) {
        this.couponHtuMapper = couponHtuMapper;
    }

    @Autowired
    public void setCouponTacMapper(CouponTacMapper couponTacMapper) {
        this.couponTacMapper = couponTacMapper;
    }

    @Override
    public CouponDto couponToCouponDto(Coupon coupon) {
        CouponDto couponDto = couponMapper.couponToCouponDto(coupon);
        couponDto.setDescription(
                messageSource.getMessage(coupon.getIndexDescription(), null, LocaleContextHolder.getLocale()));
        couponDto.setCategory(
                messageSource.getMessage(coupon.getIndexCategory(), null, LocaleContextHolder.getLocale()));
        couponDto.setName(messageSource.getMessage(coupon.getIndexName(), null, LocaleContextHolder.getLocale()));
        couponDto.setHowToUses(coupon.getHowToUses().stream().map(couponHtuMapper::couponHtuToCouponHtuDto)
                .collect(Collectors.toList()));
        couponDto.setTermAndConditions(
                coupon.getTermAndConditions().stream().map(couponTacMapper::couponTacToCouponTacDto)
                        .collect(Collectors.toList()));
        return couponDto;
    }

    @Override
    public ListCouponDto couponToListCouponDto(Coupon coupon) {
        ListCouponDto listCouponDto = couponMapper.couponToListCouponDto(coupon);
        listCouponDto.setName(messageSource.getMessage(coupon.getIndexName(), null, LocaleContextHolder.getLocale()));
        listCouponDto.setDescription(
                messageSource.getMessage(coupon.getIndexDescription(), null, LocaleContextHolder.getLocale()));
        listCouponDto.setCategory(
                messageSource.getMessage(coupon.getIndexCategory(), null, LocaleContextHolder.getLocale()));
        return listCouponDto;
    }
}
