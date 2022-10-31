package id.holigo.services.holigocouponservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigocouponservice.domain.CouponUser;

public interface CouponUserRepository extends JpaRepository<CouponUser, UUID> {
    List<CouponUser> findAllByUserId(Long userId);

    Optional<CouponUser> findByUserIdAndCouponId(Long userId, UUID couponId);

    List<CouponUser> findByUserIdAndCouponIdIn(Long userId, List<UUID> ids);
}
