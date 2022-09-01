package id.holigo.services.common.model;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto implements Serializable {
    private Long id;
    private String name;
    private String type;
    private Double rating;
    private String cityCode;
    private String hotelCode;
}

