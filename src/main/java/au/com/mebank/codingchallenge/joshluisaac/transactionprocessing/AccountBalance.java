package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

public interface AccountBalance {

  /**
   * Returns a result containing the account relative balance and no of included transactions.
   *
   * @return relative balance.
   */
  Result balance();
}
