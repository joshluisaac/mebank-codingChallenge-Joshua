package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import com.google.common.base.Preconditions;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TimeFrame {
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  @Builder
  public TimeFrame(LocalDateTime startDate, LocalDateTime endDate) {
    checkTimeFrame(startDate, endDate);
    this.startDate = startDate;
    this.endDate = endDate;
  }

  private void checkTimeFrame(LocalDateTime startDate, LocalDateTime endDate) {
    Preconditions.checkArgument(
        (endDate.isAfter(startDate) || endDate.isEqual(startDate)),
        "End date must be greater or equal to Start date");
  }
}
