package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import java.util.List;

public interface ITransactions<T extends Transaction> {

  /**
   * Returns a list of credit transactions within a {@link TransactionQueryScope}
   *
   * @return credit transactions.
   */
  List<T> creditTransactions();

  /**
   * Returns a list of debit transactions within a {@link TransactionQueryScope}
   *
   * @return debit transactions.
   */
  List<T> debitTransactions();
}
