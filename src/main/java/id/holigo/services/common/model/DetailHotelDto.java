package id.holigo.services.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@JsonIgnoreProperties
public class DetailHotelDto implements Serializable {

    private Date checkIn;

    private Date checkOut;

    private Integer roomAmount;

    private HotelDto hotel;

}
