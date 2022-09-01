package id.holigo.services.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponHotelRuleDto implements Serializable {
    private Long hotelId;
    private Long destinationId;
    private Double minimumStarRating;
    private Double maximumStarRating;
    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;
    private Date checkinPeriod;
    private Date checkoutPeriod;
    private Boolean isPercent;
    private BigDecimal value;
    private BigDecimal maximumValue;
}
