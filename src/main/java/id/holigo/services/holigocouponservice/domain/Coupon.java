package id.holigo.services.holigocouponservice.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    private String code;

    private Integer serviceId;

    private String indexName;

    private String indexDescription;

    private String indexCategory;

    private String iconUrl;

    @Column(nullable = true)
    private BigDecimal minimumFare;

    private Boolean isAutoApplied;

    private Boolean isPublic;

    @Column(nullable = true)
    private Short quantity;

    @Column(nullable = true)
    private Timestamp validAt;

    @Column(nullable = true)
    private Timestamp expiredAt;

    private String imageUrl;

    private Boolean isActive;

    private Boolean isShow;

    @Column(nullable = true)
    private Byte userVoucherLimit;

    private String ruleType;

    private String ruleId;

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
}
