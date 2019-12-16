package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.*;

import au.com.mebank.codingchallenge.joshluisaac.FakeTestData;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RelativeAccountBalanceTest {

  private RelativeAccountBalance relativeAccountBalance;
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
    ITransactions<Transaction> transactions = new Transactions(dataSet, queryScope);
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
  void shouldIncrementRelativeBalance_OnCreditTransactionEntry() {

    // when a new credit transaction is added within the time frame
    Transaction creditTrx =
        Transaction.builder()
            .transactionId("TX80003")
            .fromAccountId("ACC778899")
            .toAccountId("ACC334455")
            .createdAt(TransactionUtils.parseDate("20/10/2018 14:00:01"))
            .amount(new BigDecimal("42.00"))
            .transactionType(TransactionType.PAYMENT)
            .build();
    dataSet.add(creditTrx);
    Result result = relativeAccountBalance.balance();
    assertThat(result.getBalance()).isEqualTo(new BigDecimal("17.00"));
  }

  @Test
  void shouldIncrementTransactionsIncluded_OnCreditTransactionEntry() {

    // when a new credit transaction is added within the time frame
    Transaction creditTrx =
        Transaction.builder()
            .transactionId("TX80003")
            .fromAccountId("ACC778899")
            .toAccountId("ACC334455")
            .createdAt(TransactionUtils.parseDate("20/10/2018 14:00:01"))
            .amount(new BigDecimal("42.00"))
            .transactionType(TransactionType.PAYMENT)
            .build();
    dataSet.add(creditTrx);
    Result result = relativeAccountBalance.balance();
    assertThat(result.getTransactionsIncluded()).isEqualTo(2);
  }
}
