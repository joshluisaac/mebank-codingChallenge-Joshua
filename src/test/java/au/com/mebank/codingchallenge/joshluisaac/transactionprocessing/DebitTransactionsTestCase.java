package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.*;

import au.com.mebank.codingchallenge.joshluisaac.AbstractTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Debit transactions are transactions where money flows out of an account. These may also be
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
    dataSet = fakeDataSet();
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

  public static List<Transaction> fakeDataSet() {
    Transaction tx10001 =
        Transaction.builder()
            .transactionId("TX10001")
            .fromAccountId("ACC334455")
            .toAccountId("ACC778899")
            .createdAt(TransactionUtils.parseDate("20/10/2018 12:47:55"))
            .amount(new BigDecimal("25.00"))
            .transactionType(TransactionType.PAYMENT)
            .build();

    Transaction tx10002 =
        Transaction.builder()
            .transactionId("TX10002")
            .fromAccountId("ACC334455")
            .toAccountId("ACC998877")
            .createdAt(TransactionUtils.parseDate("20/10/2018 17:33:43"))
            .amount(new BigDecimal("10.50"))
            .transactionType(TransactionType.PAYMENT)
            .build();

    Transaction tx10003 =
        Transaction.builder()
            .transactionId("TX10003")
            .fromAccountId("ACC998877")
            .toAccountId("ACC778899")
            .createdAt(TransactionUtils.parseDate("20/10/2018 18:00:00"))
            .amount(new BigDecimal("5.00"))
            .transactionType(TransactionType.PAYMENT)
            .build();

    Transaction tx10004 =
        Transaction.builder()
            .transactionId("TX10004")
            .fromAccountId("ACC334455")
            .toAccountId("ACC998877")
            .createdAt(TransactionUtils.parseDate("20/10/2018 19:45:00"))
            .amount(new BigDecimal("10.50"))
            .transactionType(TransactionType.REVERSAL)
            .relatedTransaction("TX10002")
            .build();

    Transaction tx10005 =
        Transaction.builder()
            .transactionId("TX10005")
            .fromAccountId("ACC334455")
            .toAccountId("ACC778899")
            .createdAt(TransactionUtils.parseDate("21/10/2018 09:30:00"))
            .amount(new BigDecimal("7.25"))
            .build();

    List<Transaction> dataSet = new ArrayList<>();
    dataSet.add(tx10001);
    dataSet.add(tx10002);
    dataSet.add(tx10003);
    dataSet.add(tx10004);
    dataSet.add(tx10005);
    return dataSet;
  }
}
