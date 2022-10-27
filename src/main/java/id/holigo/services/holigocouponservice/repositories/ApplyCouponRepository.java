package id.holigo.services.holigocouponservice.repositories;

import id.holigo.services.holigocouponservice.domain.ApplyCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface ApplyCouponRepository extends JpaRepository<ApplyCoupon, UUID> {
    List<ApplyCoupon> findAllByUserIdAndCouponCodeAndCreatedAtIsBetween(Long userId, String couponCode, Timestamp startDate, Timestamp endDate);
}
