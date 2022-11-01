package id.holigo.services.common.model;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponUserDto implements Serializable {

    private UUID id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp expiredAt;

    private Integer quantity;

    private Long userId;

    private UUID couponId;

}
