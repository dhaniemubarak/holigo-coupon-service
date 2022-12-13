package id.holigo.services.holigocouponservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class HoligoCouponServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoligoCouponServiceApplication.class, args);
    }

}
