package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import lombok.NonNull;

public class RelativeAccountBalance implements AccountBalance {

  private final ITransactions<Transaction> transactions;

  public RelativeAccountBalance(@NonNull ITransactions<Transaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public Result balance() {
    List<Transaction> creditTransactions = transactions.creditTransactions();
    List<Transaction> debitTransactions = transactions.debitTransactions();
    return Result.builder()
        .balance(
            collateTotal(creditTransactions.stream())
                .subtract(collateTotal(debitTransactions.stream())))
        .transactionsIncluded(creditTransactions.size() + debitTransactions.size())
        .build();
  }

  private BigDecimal collateTotal(Stream<Transaction> transactionStream) {
    return transactionStream
        .map(Transaction::getAmount)
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }
}
