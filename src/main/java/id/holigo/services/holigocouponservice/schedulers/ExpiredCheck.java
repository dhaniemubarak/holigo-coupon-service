package id.holigo.services.holigocouponservice.schedulers;

import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.domain.CouponUser;
import id.holigo.services.holigocouponservice.repositories.CouponRepository;
import id.holigo.services.holigocouponservice.repositories.CouponUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ExpiredCheck {

    private CouponRepository couponRepository;

    private CouponUserRepository couponUserRepository;

    @Autowired
    public void setCouponUserRepository(CouponUserRepository couponUserRepository) {
        this.couponUserRepository = couponUserRepository;
    }

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Scheduled(cron = "20 0 * * * ?")
    public void publicCoupon() {
        List<Coupon> couponList = couponRepository.findAllByExpiredAtLessThanEqual(Timestamp.valueOf(LocalDateTime.now()));
        couponList.forEach(coupon -> {
            coupon.setIsActive(false);
            coupon.setIsShow(false);
        });
        couponRepository.saveAll(couponList);
    }

    @Scheduled(cron = "30 0 * * * ?")
//    @Scheduled(fixedRate = 10000)
    public void privateCoupon() {
        List<CouponUser> couponUserList = couponUserRepository.findAllByDeletedAtIsNullAndExpiredAtLessThanEqual(Timestamp.valueOf(LocalDateTime.now()));
        couponUserList.forEach(couponUser -> couponUser.setExpiredAt(Timestamp.valueOf(LocalDateTime.now())));
        couponUserRepository.saveAll(couponUserList);
    }
}
