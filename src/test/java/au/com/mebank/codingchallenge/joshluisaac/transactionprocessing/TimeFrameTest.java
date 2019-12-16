package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeFrameTest {

  private TimeFrame timeFrame;

  @BeforeEach
  void beforeTimeFrameTest() {
    LocalDateTime startDate = TransactionUtils.parseDate("20/10/2018 12:00:00");
    LocalDateTime endDate = TransactionUtils.parseDate("20/10/2018 19:00:00");
    timeFrame = TimeFrame.builder().startDate(startDate).endDate(endDate).build();
  }

  @Test
  @DisplayName("Should retrieve the specified start date")
  void shouldReturnSpecifiedStartDate() {
    assertThat(timeFrame.getStartDate())
        .isEqualTo(TransactionUtils.parseDate("20/10/2018 12:00:00"));
  }

  @Test
  @DisplayName("Should retrieve the specified end date")
  void shouldReturnSpecifiedEndDate() {
    assertThat(timeFrame.getEndDate()).isEqualTo(TransactionUtils.parseDate("20/10/2018 19:00:00"));
  }
}
