package id.holigo.services.holigocouponservice.web.controllers;

import id.holigo.services.holigocouponservice.domain.Coupon;
import id.holigo.services.holigocouponservice.domain.CouponUser;
import id.holigo.services.holigocouponservice.repositories.CouponRepository;
import id.holigo.services.holigocouponservice.repositories.CouponUserRepository;
import id.holigo.services.holigocouponservice.web.mappers.CouponUserMapper;
import id.holigo.services.holigocouponservice.web.model.CouponUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CouponUserController {

    private CouponUserRepository couponUserRepository;

    private CouponRepository couponRepository;

    private CouponUserMapper couponUserMapper;

    @Autowired
    public void setCouponUserMapper(CouponUserMapper couponUserMapper) {
        this.couponUserMapper = couponUserMapper;
    }

    @Autowired
    public void setCouponUserRepository(CouponUserRepository couponUserRepository) {
        this.couponUserRepository = couponUserRepository;
    }

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @PostMapping("/api/v1/couponUser/hotel")
    public ResponseEntity<CouponUserDto> createCoupon(@RequestBody CouponUserDto couponUserDto) {
        // check is user have the voucher
        Optional<CouponUser> fetchCouponUser = couponUserRepository.findByUserIdAndCouponId(couponUserDto.getUserId(), couponUserDto.getCouponId());
        if (fetchCouponUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        Optional<Coupon> fetchCoupon = couponRepository.findById(couponUserDto.getCouponId());
        if (fetchCoupon.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Coupon coupon = fetchCoupon.get();
        if (coupon.getCouponValue().equals(new BigDecimal("50000.00"))) {
            List<UUID> couponIds = new ArrayList<>();
            couponIds.add(UUID.fromString("1f8003ad-26dc-4760-838c-6a1bf65514c8"));
            couponIds.add(UUID.fromString("3307a306-4d52-41ad-818b-b71917bc842a"));
            couponIds.add(UUID.fromString("a20425f2-ab2d-4852-b469-db74fcd2dfe3"));
            couponIds.add(UUID.fromString("e42d6426-3d1a-4919-8c1b-f5412237f904"));
            return getCouponUserDtoResponseEntity(couponUserDto, coupon, couponIds);
        }

        if (coupon.getCouponValue().equals(new BigDecimal("100000.00"))) {
            List<UUID> couponIds = new ArrayList<>();
            couponIds.add(UUID.fromString("3f9b54d6-6756-4888-82c5-2d1acd803f03"));
            couponIds.add(UUID.fromString("dda94986-2646-418f-9ae4-f3be24caa503"));
            couponIds.add(UUID.fromString("cb5c5edb-4b43-4733-b041-b31057369a27"));
            couponIds.add(UUID.fromString("a3d37f38-b1c7-4490-bb14-32a14a00abf4"));
            return getCouponUserDtoResponseEntity(couponUserDto, coupon, couponIds);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<CouponUserDto> getCouponUserDtoResponseEntity(@RequestBody CouponUserDto couponUserDto, Coupon coupon, List<UUID> couponIds) {
        List<CouponUser> couponUsers = couponUserRepository.findByUserIdAndCouponIdIn(couponUserDto.getUserId(), couponIds);
        if (couponUsers.size() == 0) {
            LocalDateTime localDateTime = LocalDateTime.now().plusMonths(3L);
            CouponUser couponUser = couponUserMapper.couponUserDtoToCouponUser(couponUserDto);
            couponUser.setCoupon(coupon);
            couponUser.setExpiredAt(Timestamp.valueOf(localDateTime));
            couponUserRepository.save(couponUser);
        } else if (couponUsers.size() == 1) {
            couponUsers.forEach(couponUser -> {
                LocalDateTime localDateTime = couponUser.getExpiredAt().toLocalDateTime().plusMonths(3L);
                Timestamp expiredAt = Timestamp.valueOf(localDateTime);
                couponUser.setCoupon(coupon);
                couponUser.setQuantity(couponUser.getQuantity() + couponUserDto.getQuantity());
                couponUser.setExpiredAt(expiredAt);
                couponUserRepository.save(couponUser);
            });
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
