package id.holigo.services.holigocouponservice.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListCouponDto implements Serializable {

    private UUID id;

    private String code;

    private String name;

    private String description;

    private BigDecimal minimumFare;

    private String iconUrl;

    private String category;

    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp expiredAt;

    private Integer quantity;

    private String route;

    private Integer serviceId;

    private Integer productId;
}
