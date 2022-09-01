package id.holigo.services.holigocouponservice.services.transaction;

import id.holigo.services.common.model.TransactionDtoForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionServiceFeignClient transactionServiceFeignClient;

    @Autowired
    public void setTransactionServiceFeignClient(TransactionServiceFeignClient transactionServiceFeignClient) {
        this.transactionServiceFeignClient = transactionServiceFeignClient;
    }

    @Override
    public TransactionDtoForUser getDetailTransaction(UUID transactionId) {
        ResponseEntity<TransactionDtoForUser> responseEntity = transactionServiceFeignClient.getTransaction(transactionId);
        return responseEntity.getBody();
    }
}
