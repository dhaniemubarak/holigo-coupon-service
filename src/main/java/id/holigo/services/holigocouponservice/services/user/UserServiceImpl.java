package id.holigo.services.holigocouponservice.services.user;

import id.holigo.services.common.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public void setUserServiceFeignClient(UserServiceFeignClient userServiceFeignClient) {
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @Override
    public UserDto getUser(Long userId) {
        ResponseEntity<UserDto> responseEntity = userServiceFeignClient.getUserById(userId);
        return responseEntity.getBody();
    }
}
