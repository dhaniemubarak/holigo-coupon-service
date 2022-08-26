package id.holigo.services.holigocouponservice.services.transaction;

import id.holigo.services.common.TransactionDtoForUser;

import java.util.UUID;

public interface TransactionService {

    TransactionDtoForUser getDetailTransaction(UUID transactionId);
}
