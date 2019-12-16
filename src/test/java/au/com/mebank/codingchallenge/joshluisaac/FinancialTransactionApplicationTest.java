package au.com.mebank.codingchallenge.joshluisaac;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.Result;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FinancialTransactionApplicationTest {

  @BeforeEach
  public void beforeFinancialTransactionApplicationTest() {
    System.setProperty("accountId", "ACC334455");
    System.setProperty("from", "20/10/2018 12:00:00");
    System.setProperty("to", "20/10/2018 19:00:00");
    System.setProperty("csvFile", "sampleDataSet.csv");
  }

  @AfterEach
  void clearSystemProperties() {
    System.clearProperty("accountId");
    System.clearProperty("from");
    System.clearProperty("to");
    System.clearProperty("csvFile");
  }

  @Test
  @DisplayName("Should invoke transaction processing app")
  void shouldInvokeApplication() throws IOException {
    Result result = FinancialTransactionApplication.invoke();
    assertThat(result.getBalance()).isEqualTo(new BigDecimal("-25.00"));
  }

  @Test
  @DisplayName("Should throw exception when accountId is empty")
  void shouldThrowException_WhenAccountIdEmpty() {
    System.setProperty("accountId", "");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("AccountId is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when accountId is blank")
  void shouldThrowException_WhenAccountIdBlank() {
    System.setProperty("accountId", "   ");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("AccountId is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when accountId is null")
  void shouldThrowException_WhenAccountIdIsNull() {
    System.clearProperty("accountId");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable).isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Should throw exception when from date is empty")
  void shouldThrowException_WhenStartDateEmpty() {
    System.setProperty("from", "");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("from is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when from date is blank")
  void shouldThrowException_WhenStartDateBlank() {
    System.setProperty("from", "   ");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("from is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when from date is null")
  void shouldThrowException_WhenStartDateIsNull() {
    System.clearProperty("from");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable).isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Should throw exception when to date is empty")
  void shouldThrowException_WhenEndDateEmpty() {
    System.setProperty("to", "");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("to is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when to date is blank")
  void shouldThrowException_WhenEndDateBlank() {
    System.setProperty("to", "   ");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("to is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when to date is null")
  void shouldThrowException_WhenEndDateIsNull() {
    System.clearProperty("to");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable).isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV is empty")
  void shouldThrowException_WhenCsvEmpty() {
    System.setProperty("csvFile", "");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("csvFilePath is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when CSV file is blank")
  void shouldThrowException_WhenCsvBlank() {
    System.setProperty("csvFile", "   ");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("csvFilePath is either blank or empty");
  }

  @Test
  @DisplayName("Should throw exception when CSV is null")
  void shouldThrowException_WhenCsvIsNull() {
    System.clearProperty("csvFile");
    Throwable throwable = catchThrowable(FinancialTransactionApplication::invoke);
    assertThat(throwable).isInstanceOf(NullPointerException.class);
  }
}
