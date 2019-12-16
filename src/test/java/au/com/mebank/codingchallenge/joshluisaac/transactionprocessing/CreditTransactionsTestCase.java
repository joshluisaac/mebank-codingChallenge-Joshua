package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.mebank.codingchallenge.joshluisaac.AbstractTest;
import au.com.mebank.codingchallenge.joshluisaac.FakeTestData;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
          + "an increase credit transactions and impact relative balance computation provided it is within the given time frame")
  @Test
  void creditTransactionsShouldIncrease_OnNewCreditTransactionEntryWithInTimeFrame() {

    // when a credit transaction is added within the given time frame
    LocalDateTime transactionDate = TransactionUtils.parseDate("20/10/2018 14:00:01");
    dataSet.add(
        makePayment("TX80003", "ACC778899", "ACC334455", transactionDate, new BigDecimal("42.00")));

    // i expect the size of credit transactions to increase
    assertThat(transactions.creditTransactions().size()).isEqualTo(1);
  }

  @DisplayName(
      "Adding a credit transaction outside the given time frame has no effect on relative balance computation.")
  @Test
  void creditTransactionsOutsideTimeFrame_ShouldHaveNoEffectOnComputation() {

    // when a credit transaction is added outside the given time frame
    LocalDateTime transactionDate = TransactionUtils.parseDate("20/10/2018 21:00:01");
    dataSet.add(
        makePayment(
            "TX59008", "ACC778899", "ACC334455", transactionDate, new BigDecimal("100.00")));

    // i expect the size of credit transactions to remain unaffected.
    assertThat(transactions.creditTransactions().size()).isEqualTo(0);
  }

  @DisplayName("Reversed transactions has no observable effect on the credit side of things.")
  @Test
  void reversedTransactionsShouldHaveNoEffectOnCreditSide() {

    // when i make a payment on start of time frame
    LocalDateTime paymentCreatedDate = TransactionUtils.parseDate("20/10/2018 12:00:00");
    Transaction payment =
        makePayment(
            "TX120001", "ACC778899", "ACC334455", paymentCreatedDate, new BigDecimal("22.50"));

    // then i reverse the payment transaction after time frame
    LocalDateTime reversedCreatedDate = TransactionUtils.parseDate("24/10/2018 21:47:55");
    Transaction paymentReversal =
        reversePayment(
            "TX120002",
            "ACC778899",
            "ACC334455",
            reversedCreatedDate,
            new BigDecimal("22.50"),
            "TX120001");

    transactions.setTransactionDataSet(List.of(payment, paymentReversal));
    // i expect the size of credit transactions to remain the same
    assertThat(transactions.creditTransactions().size()).isEqualTo(1);
  }
}
