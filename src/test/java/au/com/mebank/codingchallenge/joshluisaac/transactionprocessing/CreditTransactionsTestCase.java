package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.mebank.codingchallenge.joshluisaac.AbstractTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import au.com.mebank.codingchallenge.joshluisaac.FakeTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Credit transactions are transactions in which money flows into an account. These may also be
 * referred to as Accounts receivable (AR). It uses the toAccountId to identify these cases"
 */
@DisplayName("Credit transactions test cases")
public class CreditTransactionsTestCase extends AbstractTest {

  private Transactions transactions;
  private static List<Transaction> dataSet;

  @BeforeEach
  public void beforeTransactionsTestForCreditTransactionCases() {
    TimeFrame timeFrame =
        TimeFrame.builder()
            .startDate(TransactionUtils.parseDate("20/10/2018 12:00:00"))
            .endDate(TransactionUtils.parseDate("20/10/2018 19:00:00"))
            .build();
    TransactionQueryScope queryScope =
        TransactionQueryScope.builder().accountId("ACC334455").timeFrame(timeFrame).build();
    dataSet = FakeTestData.fakeDataSet();
    transactions = new Transactions(dataSet, queryScope);
  }

  @DisplayName("Should return the size of credit transactions")
  @Test
  void testShouldReturnCreditTransactions() {
    assertThat(transactions.creditTransactions().size()).isEqualTo(0);
  }

  @DisplayName(
      "Adding a credit transaction should cause "
          + "an increase in relative balance provided it is within the given time frame")
  @Test
  void creditTransactionsShouldIncrease_OnNewCreditTransactionEntryWithInTimeFrame() {

    // when a credit transaction is added within the given time frame
    Transaction creditTransactionWithinTimeFrame =
        Transaction.builder()
            .transactionId("TX80003")
            .fromAccountId("ACC778899")
            .toAccountId("ACC334455")
            .createdAt(TransactionUtils.parseDate("20/10/2018 14:00:01"))
            .amount(new BigDecimal("42.00"))
            .transactionType(TransactionType.PAYMENT)
            .build();
    dataSet.add(creditTransactionWithinTimeFrame);

    // i expect the size of credit transactions to increase
    assertThat(transactions.creditTransactions().size()).isEqualTo(1);
  }

  @DisplayName(
      "Adding a credit transaction outside the given time frame has no effect on relative balance computation.")
  @Test
  void creditTransactionsOutsideTimeFrame_ShouldHaveNoEffectOnComputation() {

    // when a credit transaction is added outside the given time frame
    Transaction creditTransactionOutsideTimeFrame =
        Transaction.builder()
            .transactionId("TX59008")
            .fromAccountId("ACC778899")
            .toAccountId("ACC334455")
            .createdAt(TransactionUtils.parseDate("20/10/2018 21:00:01"))
            .amount(new BigDecimal("100.00"))
            .transactionType(TransactionType.PAYMENT)
            .build();

    dataSet.add(creditTransactionOutsideTimeFrame);
    // i expect the size of credit transactions to remain the same
    assertThat(transactions.creditTransactions().size()).isEqualTo(0);
  }

  @DisplayName("Reversed transactions has no observable effect on the credit side of things.")
  @Test
  void reversedTransactionsShouldHaveNoEffectOnCreditSide() {

    // when a reversed transaction is added
    Transaction creditTransactionOutsideTimeFrame =
        Transaction.builder()
            .transactionId("TX59008")
            .fromAccountId("ACC778899")
            .toAccountId("ACC334455")
            .createdAt(TransactionUtils.parseDate("20/10/2018 17:00:01"))
            .amount(new BigDecimal("100.00"))
            .transactionType(TransactionType.REVERSAL)
            .build();

    dataSet.add(creditTransactionOutsideTimeFrame);
    // i expect the size of credit transactions to remain the same
    assertThat(transactions.creditTransactions().size()).isEqualTo(0);
  }


}
