package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.*;

import au.com.mebank.codingchallenge.joshluisaac.AbstractTest;
import au.com.mebank.codingchallenge.joshluisaac.FakeTestData;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RelativeAccountBalanceTest extends AbstractTest {

  private AccountBalance relativeAccountBalance;
  private Transactions transactions;
  private static List<Transaction> dataSet;

  @BeforeEach
  public void beforeRelativeAccountBalanceTest() {
    TimeFrame timeFrame =
        TimeFrame.builder()
            .startDate(TransactionUtils.parseDate("20/10/2018 12:00:00"))
            .endDate(TransactionUtils.parseDate("20/10/2018 19:00:00"))
            .build();
    TransactionQueryScope queryScope =
        TransactionQueryScope.builder().accountId("ACC334455").timeFrame(timeFrame).build();
    dataSet = FakeTestData.fakeDataSet();
    transactions = new Transactions(dataSet, queryScope);
    relativeAccountBalance = new RelativeAccountBalance(transactions);
  }

  @Test
  void shouldReturnRelativeBalance() {
    Result result = relativeAccountBalance.balance();
    assertThat(result.getBalance()).isEqualTo(new BigDecimal("-25.00"));
  }

  @Test
  void shouldReturnTransactionsIncluded() {
    Result result = relativeAccountBalance.balance();
    assertThat(result.getTransactionsIncluded()).isEqualTo(1);
  }

  @Test
  void shouldEvaluate_PaymentAndReversalTransactions() {

    List<Transaction> data = new ArrayList<>();
    LocalDateTime transactionDate = TransactionUtils.parseDate("20/10/2018 14:00:01");
    LocalDateTime reversalTransactionDate = TransactionUtils.parseDate("27/10/2018 09:10:55");

    // when i initiate 3 payments
    data.add(
        makePayment("TX80003", "ACC334455", "ACC778899", transactionDate, new BigDecimal("10.00")));
    data.add(
        makePayment("TX80004", "ACC334455", "ACC778899", transactionDate, new BigDecimal("5.50")));
    data.add(
        makePayment("TX80005", "ACC334455", "ACC778899", transactionDate, new BigDecimal("25.01")));

    // and 1 reversal
    data.add(
        reversePayment(
            "TX80006",
            "ACC334455",
            "ACC778899",
            reversalTransactionDate,
            new BigDecimal("5.50"),
            "TX80004"));

    transactions.setTransactionDataSet(data);
    Result result = relativeAccountBalance.balance();
    int compareBalance = (result.getBalance().compareTo(new BigDecimal("-35.01")));
    boolean actualResult = compareBalance == 0 && (result.getTransactionsIncluded() == 2);
    assertThat(actualResult).isTrue();
  }

  @Test
  void shouldIncrementRelativeBalance_OnCreditTransactionEntry() {

    // when a new credit transaction is added within the time frame
    LocalDateTime transactionDate = TransactionUtils.parseDate("20/10/2018 14:00:01");
    dataSet.add(
        makePayment("TX80003", "ACC778899", "ACC334455", transactionDate, new BigDecimal("42.00")));

    // then it should increment relative balance
    Result result = relativeAccountBalance.balance();
    assertThat(result.getBalance()).isEqualTo(new BigDecimal("17.00"));
  }

  @Test
  void shouldIncrementTransactionsIncluded_OnCreditTransactionEntry() {

    // when a new credit transaction is added within the time frame
    LocalDateTime transactionDate = TransactionUtils.parseDate("20/10/2018 14:00:01");
    dataSet.add(
        makePayment("TX80003", "ACC778899", "ACC334455", transactionDate, new BigDecimal("42.00")));

    // then it should increase the transactions included
    Result result = relativeAccountBalance.balance();
    assertThat(result.getTransactionsIncluded()).isEqualTo(2);
  }
}
