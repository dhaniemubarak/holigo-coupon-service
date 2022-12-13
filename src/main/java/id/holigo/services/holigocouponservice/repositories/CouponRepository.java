package id.holigo.services.holigocouponservice.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigocouponservice.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    List<Coupon> findAllByDeletedAtIsNullAndIsPublicAndIsShow(Boolean isPublic, Boolean isShow);

    List<Coupon> findAllByDeletedAtIsNullAndIsPublicAndIsShowAndServiceIdIn(Boolean isPublic, Boolean isShow, List<Integer> serviceIds);

    List<Coupon> findAllByDeletedAtIsNullAndIsPublicAndIsShowAndServiceIdNotIn(Boolean isPublic, Boolean isShow,
                                                             List<Integer> serviceIds);

    Optional<Coupon> findByCode(String code);

    List<Coupon> findAllByExpiredAtLessThanEqual(Timestamp timestamp);

}
