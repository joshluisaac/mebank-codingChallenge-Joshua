package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.*;

import au.com.mebank.codingchallenge.joshluisaac.AbstractTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionUtilsTest extends AbstractTest {

  @Test
  public void testShouldParseDate() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 19:45:11");
    assertThat(testDate).isEqualTo(LocalDateTime.of(2018, 10, 20, 19, 45, 11));
  }

  @Test
  @DisplayName("Should throw exception when date is invalid")
  public void testShouldThrowIllegalArgumentException_WhenRawDateIsInvalid() {
    Throwable throwable = catchThrowable(() -> TransactionUtils.parseDate("20/10/2018 19:45"));
    assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void parsedCsv_ShouldReturnTransactionId() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT");
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 12:47:55");
    assertThat(actualTransaction.getTransactionId()).isEqualTo("TX10001");
  }

  @Test
  public void parsedCsv_ShouldReturnFromAccountId() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT");
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 12:47:55");
    assertThat(actualTransaction.getFromAccountId()).isEqualTo("ACC334455");
  }

  @Test
  public void parsedCsv_ShouldReturnToAccountId() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT");
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 12:47:55");
    assertThat(actualTransaction.getToAccountId()).isEqualTo("ACC778899");
  }

  @Test
  public void parsedCsv_ShouldReturnCreatedAt() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT");
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 12:47:55");
    assertThat(actualTransaction.getCreatedAt())
        .isEqualTo(TransactionUtils.parseDate("20/10/2018 12:47:55"));
  }

  @Test
  public void parsedCsv_ShouldReturnTransactionAmount() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT");
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 12:47:55");
    assertThat(actualTransaction.getAmount()).isEqualTo(new BigDecimal("25.00"));
  }

  @Test
  public void parsedCsv_ShouldReturnTransactionType() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT");
    LocalDateTime createdAt = TransactionUtils.parseDate("20/10/2018 12:47:55");
    assertThat(actualTransaction.getTransactionType()).isEqualTo(TransactionType.PAYMENT);
  }

  @Test
  public void testParseCsvTransaction_WithRelatedTransaction() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL, TX10002");
    assertThat(actualTransaction.getRelatedTransaction()).isEqualTo("TX10002");
  }

  @Test
  public void parsedCsv_ShouldReturnReversalTransactionType() {
    Transaction actualTransaction =
        TransactionUtils.parseCsvTransaction(
            "TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL, TX10002");
    assertThat(actualTransaction.getTransactionType()).isEqualTo(TransactionType.REVERSAL);
  }
}
