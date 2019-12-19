package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import au.com.mebank.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Transaction {

  private final String transactionId;
  private final String fromAccountId;
  private final String toAccountId;
  private final LocalDateTime createdAt;
  private final BigDecimal amount;
  private final TransactionType transactionType;
  private final String relatedTransaction;

  public boolean isAfter(TimeFrame timeFrame) {
    return createdAt.isAfter(timeFrame.getStartDate());
  }

  public boolean isWithin(TimeFrame timeFrame) {
    if (createdAt.isAfter(timeFrame.getStartDate()) && createdAt.isBefore(timeFrame.getEndDate()))
      return true;
    return (createdAt.isEqual(timeFrame.getStartDate())
        || createdAt.isEqual(timeFrame.getEndDate()));
  }

  public static Transaction createPayment(
      String fromAccountId, String toAccountId, BigDecimal amount) {
    return Transaction.builder()
        .transactionId(UuidUtils.create().toString())
        .createdAt(LocalDateTime.now())
        .fromAccountId(fromAccountId)
        .toAccountId(toAccountId)
        .amount(amount)
        .transactionType(TransactionType.PAYMENT)
        .build();
  }

  public static Transaction createReversal(
      String fromAccountId, String toAccountId, BigDecimal amount, String relatedTransaction) {
    return Transaction.builder()
        .transactionId(UuidUtils.create().toString())
        .createdAt(LocalDateTime.now())
        .fromAccountId(fromAccountId)
        .toAccountId(toAccountId)
        .amount(amount)
        .transactionType(TransactionType.REVERSAL)
        .relatedTransaction(relatedTransaction)
        .build();
  }
}
