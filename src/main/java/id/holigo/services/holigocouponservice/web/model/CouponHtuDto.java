package id.holigo.services.holigocouponservice.web.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponHtuDto implements Serializable {
    
    private String caption;

    private String imageUrl;
}
