package id.holigo.services.holigocouponservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HoligoCouponServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoligoCouponServiceApplication.class, args);
	}

}
