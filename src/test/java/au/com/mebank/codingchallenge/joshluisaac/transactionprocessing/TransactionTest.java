package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionTest {

  private Transaction.TransactionBuilder transactionBuilder;

  private TimeFrame timeFrame;

  @BeforeEach
  public void beforeTransactionTest() {
    transactionBuilder =
        Transaction.builder()
            .transactionId("TX10001")
            .fromAccountId("ACC334455")
            .toAccountId("ACC778899")
            .amount(new BigDecimal("25.00"))
            .transactionType(TransactionType.PAYMENT);
    LocalDateTime startDate = TransactionUtils.parseDate("20/10/2018 12:00:00");
    LocalDateTime endDate = TransactionUtils.parseDate("20/10/2018 19:00:00");
    timeFrame = TimeFrame.builder().startDate(startDate).endDate(endDate).build();
  }

  @Test
  @DisplayName("Transaction created on exactly the start of time frame should return true")
  void shouldReturnTrueForTransactionCreatedOnStartOfTimeFrame() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 12:00:00");
    boolean actual = transactionBuilder.createdAt(testDate).build().isWithin(timeFrame);
    assertThat(actual).isTrue();
  }

  @Test
  @DisplayName("Transaction created on exactly the end of time frame should return true")
  void shouldReturnTrueForTransactionCreatedOnEndOfTimeFrame() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 19:00:00");
    boolean actual = transactionBuilder.createdAt(testDate).build().isWithin(timeFrame);
    assertThat(actual).isTrue();
  }

  @Test
  @DisplayName("Transaction created after start but before end of time frame should return true")
  void shouldReturnTrueForTransactionCreatedAfterStartOfTimeFrame() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 12:47:55");
    boolean actual = transactionBuilder.createdAt(testDate).build().isWithin(timeFrame);
    assertThat(actual).isTrue();
  }

  @Test
  @DisplayName("Transaction created on before the start of time frame should return false")
  void shouldReturnTrueForTransactionCreatedBeforeStartOfTimeFrame() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 11:59:59");
    boolean actual = transactionBuilder.createdAt(testDate).build().isWithin(timeFrame);
    assertThat(actual).isFalse();
  }

  @Test
  @DisplayName("Transaction created on after the end of time frame should return false")
  void shouldReturnTrueForTransactionCreatedAfterEndOfTimeFrame() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 19:00:01");
    boolean actual = transactionBuilder.createdAt(testDate).build().isWithin(timeFrame);
    assertThat(actual).isFalse();
  }

  @Test
  @DisplayName("Transaction created after time frame should return true")
  void shouldReturnTrueForTransactionCreatedAfterTimeFrame() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 19:45:00");
    boolean actual = transactionBuilder.createdAt(testDate).build().isAfter(timeFrame);
    assertThat(actual).isTrue();
  }

  @Test
  @DisplayName("Should retrieve the specified transactionId")
  void shouldReturnSpecifiedTransactionId() {
    assertThat(transactionBuilder.build().getTransactionId()).isEqualTo("TX10001");
  }

  @Test
  @DisplayName("Should retrieve the specified transaction amount")
  void shouldReturnSpecifiedTransactionAmount() {
    assertThat(transactionBuilder.build().getAmount()).isEqualTo(new BigDecimal("25.00"));
  }

  @Test
  @DisplayName("Should retrieve the specified transaction date")
  void shouldReturnSpecifiedTransactionDate() {
    LocalDateTime testDate = TransactionUtils.parseDate("20/10/2018 11:59:59");
    assertThat(transactionBuilder.createdAt(testDate).build().getCreatedAt())
        .isEqualTo(TransactionUtils.parseDate("20/10/2018 11:59:59"));
  }
}
