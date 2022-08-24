package id.holigo.services.holigocouponservice.domain;

import id.holigo.services.common.UserGroupEnum;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    private String indexName;

    private String indexDescription;

    private String indexCategory;

    private String iconUrl;

    private BigDecimal minimumFare;

    private Boolean isAutoApplied;

    private Boolean isPublic;

    private Short quantity;

    private Timestamp validAt;

    private Timestamp expiredAt;

    private String imageUrl;

    private Boolean isActive;

    private Boolean isShow;

    private Boolean isAndroid;

    private Boolean isIos;

    private String indexShortTerms;

    private Byte userVoucherLimit;

    private String ruleType;

    @Lob
    private String ruleValue;

    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CouponHtu> howToUses;

    @OneToMany(mappedBy = "coupon")
    @ToString.Exclude
    private List<CouponTac> termAndConditions;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;

    private String route;

    private String indexLabel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Coupon coupon = (Coupon) o;
        return id != null && Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
