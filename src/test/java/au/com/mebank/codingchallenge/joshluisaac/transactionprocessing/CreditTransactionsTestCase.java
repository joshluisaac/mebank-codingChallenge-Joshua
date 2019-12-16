package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.mebank.codingchallenge.joshluisaac.AbstractTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Credit transactions are transactions where money flows into an account. These may also be
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
    dataSet = fakeDataSet();
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
