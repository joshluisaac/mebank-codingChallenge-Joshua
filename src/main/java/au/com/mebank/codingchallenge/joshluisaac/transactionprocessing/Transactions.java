package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Transactions implements ITransactions<Transaction> {

  private List<Transaction> transactionDataSet;
  private final TransactionQueryScope queryScope;
  private Supplier<Stream<Transaction>> streamSupplier;

  public Transactions(List<Transaction> transactionDataSet, TransactionQueryScope queryScope) {
    this.transactionDataSet = transactionDataSet;
    this.queryScope = queryScope;
  }

  @Override
  public List<Transaction> creditTransactions() {
    return accountTransactions(transaction -> transaction.getToAccountId().equals(accountId()))
        .filter(Transactions::isPaymentTransactionType)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public List<Transaction> debitTransactions() {
    return accountTransactions(transaction -> transaction.getFromAccountId().equals(accountId()))
        .filter(Predicate.not(transaction -> isReversedTransaction(transaction.getTransactionId())))
        .filter(Transactions::isPaymentTransactionType)
        .collect(Collectors.toUnmodifiableList());
  }

  private boolean isReversedTransaction(String transactionId) {
    return reversedTransactions().contains(transactionId);
  }

  private List<String> reversedTransactions() {
    return transactionDataSet
        .stream()
        .filter(transaction -> transaction.getFromAccountId().equals(accountId()))
        .filter(transaction -> transaction.getTransactionType() == TransactionType.REVERSAL)
        .filter(
            transaction ->
                (transaction.isAfter(queryScope.getTimeFrame())
                    || transaction.isWithin(queryScope.getTimeFrame())))
        .map(Transaction::getRelatedTransaction)
        .collect(Collectors.toUnmodifiableList());
  }

  private Stream<Transaction> accountTransactions(Predicate<Transaction> pred) {
    return timeFrameStreamSupplier().get().filter(pred);
  }

  private static boolean isPaymentTransactionType(Transaction transaction) {
    return transaction.getTransactionType() == TransactionType.PAYMENT;
  }

  private Supplier<Stream<Transaction>> timeFrameStreamSupplier() {
    streamSupplier =
        (streamSupplier != null)
            ? streamSupplier
            : () ->
                transactionDataSet
                    .stream()
                    .filter(transaction -> transaction.isWithin(queryScope.getTimeFrame()));
    return streamSupplier;
  }

  private String accountId() {
    return queryScope.getAccountId();
  }

  public void setTransactionDataSet(List<Transaction> dataSet) {
    this.transactionDataSet = dataSet;
  }
}
