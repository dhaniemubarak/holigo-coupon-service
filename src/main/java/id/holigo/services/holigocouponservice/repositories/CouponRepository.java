package id.holigo.services.holigocouponservice.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigocouponservice.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

}
