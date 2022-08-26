package id.holigo.services.holigocouponservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigocouponservice.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    List<Coupon> findAllByIsPublicAndIsShow(Boolean isPublic, Boolean isShow);

    List<Coupon> findAllByIsPublicAndIsShowAndServiceIdIn(Boolean isPublic, Boolean isShow, List<Integer> serviceIds);

    List<Coupon> findAllByIsPublicAndIsShowAndServiceIdNotIn(Boolean isPublic, Boolean isShow,
                                                             List<Integer> serviceIds);

    Optional<Coupon> findByCode(String code);

}
