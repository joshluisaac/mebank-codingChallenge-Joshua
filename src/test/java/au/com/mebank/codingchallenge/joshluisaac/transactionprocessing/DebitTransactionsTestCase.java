package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.*;

import au.com.mebank.codingchallenge.joshluisaac.AbstractTest;
import au.com.mebank.codingchallenge.joshluisaac.FakeTestData;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Debit transactions are transactions in which money flows out of an account. These may also be
 * referred to as Accounts payable (AP). It uses the fromAccountId to identify these cases"
 */
@DisplayName("Debit transactions test cases")
public class DebitTransactionsTestCase extends AbstractTest {

  private Transactions transactions;
  private TransactionQueryScope queryScope;
  private static List<Transaction> dataSet;

  @BeforeEach
  public void beforeTransactionsTestForDebitCases() {
    TimeFrame timeFrame =
        TimeFrame.builder()
            .startDate(TransactionUtils.parseDate("20/10/2018 12:00:00"))
            .endDate(TransactionUtils.parseDate("20/10/2018 19:00:00"))
            .build();
    queryScope =
        TransactionQueryScope.builder().accountId("ACC334455").timeFrame(timeFrame).build();
    dataSet = FakeTestData.fakeDataSet();
    transactions = new Transactions(dataSet, queryScope);
  }

  @DisplayName("Should return the size of debit transactions")
  @Test
  void testShouldReturnDebitTransactions() {
    assertThat(transactions.debitTransactions().size()).isEqualTo(1);
  }

  @DisplayName(
      "Adding a new debit transaction should cause "
          + "an increase provided it was within the given time frame")
  @Test
  void debitTransactionsShouldIncrease_OnNewDebitTransactionEntryWithInTimeFrame() {
    // when a new debit transaction is added within the given time frame
    Transaction debitTransactionWithinTimeFrame =
        makePayment(
            "TX80003",
            "ACC334455",
            "ACC778899",
            TransactionUtils.parseDate("20/10/2018 14:00:01"),
            new BigDecimal("42.00"));
    dataSet.add(debitTransactionWithinTimeFrame);

    // i expect the size of debit transactions to increase
    assertThat(transactions.debitTransactions().size()).isEqualTo(2);
  }

  @DisplayName(
      "Adding a new debit transaction outside the given time frame has no effect on relative balance computation.")
  @Test
  void debitTransactionsOutsideTimeFrame_ShouldHaveNoEffectOnComputation() {
    // when a new debit transaction is added outside the given time frame
    Transaction debitTransactionOutsideTimeFrame =
        makePayment(
            "TX59008",
            "ACC334455",
            "ACC778899",
            TransactionUtils.parseDate("20/10/2018 21:00:01"),
            new BigDecimal("100.00"));
    dataSet.add(debitTransactionOutsideTimeFrame);

    // i expect the size of debit transactions to remain the same
    assertThat(transactions.debitTransactions().size()).isEqualTo(1);
  }

  @DisplayName(
      "Reversing a transaction before start of time frame has no effect on relative balance computation.")
  @Test
  void reversingTransactionBeforeStartOfTimeFrame_ShouldHaveNoEffect() {
    // before start of time frame
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 10:12:22");

    // when a reversed transaction is added before start of the given time frame
    Transaction paymentReversal =
        reversePayment(
            "TX10006", "ACC334455", "ACC998877", createdAt, new BigDecimal("22.50"), "TX10001");
    dataSet.add(paymentReversal);

    // i expect reversed transaction should be ignored
    // because it occurred before the related transaction
    assertThat(transactions.debitTransactions().size()).isEqualTo(1);
  }

  @DisplayName(
      "Reversing a transaction on start of time frame should omit reversed transaction "
          + "and also affect relative balance computation.")
  @Test
  void reversingTransactionOnStartOfTimeFrame_ShouldOmitReversedTransaction() {
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 12:00:00");
    // when i make a payment on start of time frame
    Transaction payment =
        makePayment("TX120001", "ACC334455", "ACC998877", createdAt, new BigDecimal("22.50"));

    // then i reverse the payment transaction on the same date
    Transaction paymentReversal =
        reversePayment(
            "TX120002", "ACC334455", "ACC998877", createdAt, new BigDecimal("22.50"), "TX120001");

    transactions.setTransactionDataSet(List.of(payment, paymentReversal));
    // i expect the payment transaction should be omitted
    assertThat(transactions.debitTransactions().size()).isEqualTo(0);
  }

  @DisplayName(
      "Reversing a transaction within the specified time frame should omit reversed transaction "
          + "and also affect relative balance computation.")
  @Test
  void reversingTransactionWithinTimeFrame_ShouldOmitReversedTransaction() {
    LocalDateTime paymentCreatedDate = TransactionUtils.parseDate("20/10/2018 12:00:00");
    LocalDateTime reversedCreatedDate = TransactionUtils.parseDate("20/10/2018 12:47:55");

    // when i make a payment on start of time frame
    Transaction payment =
        makePayment(
            "TX120001", "ACC334455", "ACC998877", paymentCreatedDate, new BigDecimal("22.50"));

    // then i reverse the payment transaction during the time frame
    Transaction paymentReversal =
        reversePayment(
            "TX120002",
            "ACC334455",
            "ACC998877",
            reversedCreatedDate,
            new BigDecimal("22.50"),
            "TX120001");

    transactions.setTransactionDataSet(List.of(payment, paymentReversal));
    // i expect the payment transaction should be omitted
    assertThat(transactions.debitTransactions().size()).isEqualTo(0);
  }

  @DisplayName(
      "Reversing a transaction after the specified time frame should omit reversed transaction "
          + "and also affect relative balance computation.")
  @Test
  void reversingTransactionAfterTimeFrame_ShouldOmitReversedTransaction() {
    LocalDateTime paymentCreatedDate = TransactionUtils.parseDate("20/10/2018 12:00:00");
    LocalDateTime reversedCreatedDate = TransactionUtils.parseDate("24/10/2018 21:47:55");

    // when i make a payment on start of time frame
    Transaction payment =
        makePayment(
            "TX120001", "ACC334455", "ACC998877", paymentCreatedDate, new BigDecimal("22.50"));

    // then i reverse the payment transaction after time frame
    Transaction paymentReversal =
        reversePayment(
            "TX120002",
            "ACC334455",
            "ACC998877",
            reversedCreatedDate,
            new BigDecimal("22.50"),
            "TX120001");

    transactions.setTransactionDataSet(List.of(payment, paymentReversal));
    // i expect the payment transaction should be omitted
    assertThat(transactions.debitTransactions().size()).isEqualTo(0);
  }
}
