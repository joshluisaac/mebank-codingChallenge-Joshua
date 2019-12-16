package au.com.mebank.codingchallenge.joshluisaac;

import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.Transaction;
import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AbstractTest {

  protected Transaction makePayment(
      String transactionId,
      String fromAccountId,
      String toAccountId,
      LocalDateTime createdAt,
      BigDecimal amount) {
    return Transaction.builder()
        .transactionId(transactionId)
        .fromAccountId(fromAccountId)
        .toAccountId(toAccountId)
        .createdAt(createdAt)
        .amount(amount)
        .transactionType(TransactionType.PAYMENT)
        .build();
  }

  protected Transaction reversePayment(
      String transactionId,
      String fromAccountId,
      String toAccountId,
      LocalDateTime createdAt,
      BigDecimal amount,
      String relatedTransaction) {
    return Transaction.builder()
        .transactionId(transactionId)
        .fromAccountId(fromAccountId)
        .toAccountId(toAccountId)
        .createdAt(createdAt)
        .amount(amount)
        .transactionType(TransactionType.REVERSAL)
        .relatedTransaction(relatedTransaction)
        .build();
  }
}
