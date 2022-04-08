package id.holigo.services.holigocouponservice.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigocouponservice.domain.CouponUser;

public interface CouponUserRepository extends JpaRepository<CouponUser, UUID> {
    List<CouponUser> findAllByUserId(Long userId);
}
