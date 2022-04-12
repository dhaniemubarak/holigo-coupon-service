package id.holigo.services.holigocouponservice.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto implements Serializable {

    private UUID id;

    private String code;

    private String name;

    private String description;

    private String iconUrl;

    private String category;

    private Boolean isAutoApplied;

    private BigDecimal minimumFare;

    private Short quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp validAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp expiredAt;

    private String imageUrl;

    private List<CouponHtuDto> howToUses;

    private List<CouponTacDto> termAndConditions;

    private String route;
}
