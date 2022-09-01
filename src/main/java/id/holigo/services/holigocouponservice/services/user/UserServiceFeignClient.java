package id.holigo.services.holigocouponservice.services.user;

import id.holigo.services.common.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("holigo-user-service")
public interface UserServiceFeignClient {
    String USER_DETAIL_BY_ID = "/api/v1/completeUsers/{id}";
    @RequestMapping(method = RequestMethod.GET, value = USER_DETAIL_BY_ID)
    ResponseEntity<UserDto> getUserById(@PathVariable Long id);
}
