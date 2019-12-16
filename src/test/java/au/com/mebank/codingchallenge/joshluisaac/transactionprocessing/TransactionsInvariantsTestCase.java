package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import au.com.mebank.codingchallenge.joshluisaac.FakeTestData;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionsInvariantsTestCase {

  private Transactions transactions;
  private static List<Transaction> dataSet;
  private TransactionQueryScope queryScope;

  @BeforeEach
  public void beforeTransactionsInvariantsTestCase() {
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

  @Test
  @DisplayName("Should throw exception when dataset is null")
  void shouldThrowNullPointer_WhenDataSetIsNull() {
    Throwable throwable = catchThrowable(() -> transactions = new Transactions(null, queryScope));
    assertThat(throwable).isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Should throw exception when transaction scope is null")
  void shouldThrowNullPointer_WhenTransactionScopeIsNull() {
    Throwable throwable = catchThrowable(() -> transactions = new Transactions(dataSet, null));
    assertThat(throwable).isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Should throw exception when dataset is empty")
  void shouldThrowIllegalArgumentException_WhenTransactionDataSetIsEmpty() {
    Throwable throwable =
        catchThrowable(() -> transactions = new Transactions(new ArrayList<>(), queryScope));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Transaction dataset is empty");
  }
}
