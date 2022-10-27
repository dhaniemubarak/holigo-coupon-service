package id.holigo.services.holigocouponservice.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import id.holigo.services.common.model.UserGroupEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Coupon {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Convert(converter = UserGroupEnumConverter.class)
    private UserGroupEnum userGroup;

    private String code;

    private Integer serviceId;

    private Integer productId;

    private String indexName;

    private String indexDescription;

    private String indexCategory;

    private String iconUrl;

    private BigDecimal minimumFare;

    private Boolean isAutoApplied;

    private Boolean isPublic;

    private Integer quantity;

    private Timestamp validAt;

    private Timestamp expiredAt;

    private String imageUrl;

    private Boolean isActive;

    private Boolean isShow;

    private Boolean isAndroid;

    private Boolean isIos;

    private Boolean isLogin;

    private Boolean isFreeAdmin;

    private Boolean isFreeServiceFee;

    private Boolean isPercent;

    private Boolean isOfficialAccount;

    private BigDecimal couponValue;

    private BigDecimal maximumCouponValue;

    private String indexShortTerms;

    private Integer userVoucherLimit;

    private String ruleType;

    @Lob
    private String ruleValue;

    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<CouponHtu> howToUses;

    @OneToMany(mappedBy = "coupon")
    private List<CouponTac> termAndConditions;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;

    private String route;

    private String indexLabel;
}
