package id.holigo.services.holigocouponservice.repositories;

import id.holigo.services.holigocouponservice.domain.ApplyCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyCouponRepository extends JpaRepository<ApplyCoupon, UUID> {
}
