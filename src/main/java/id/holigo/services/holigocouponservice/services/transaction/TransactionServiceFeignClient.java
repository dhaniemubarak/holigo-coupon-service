package id.holigo.services.holigocouponservice.services.transaction;

import id.holigo.services.common.model.TransactionDtoForUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient("holigo-transaction-service")
public interface TransactionServiceFeignClient {

    String TRANSACTION_DETAIL_BY_ID = "/api/v1/transactions/{id}";

    @RequestMapping(method = RequestMethod.GET, value = TRANSACTION_DETAIL_BY_ID)
    ResponseEntity<TransactionDtoForUser> getTransaction(@PathVariable UUID id);

}
