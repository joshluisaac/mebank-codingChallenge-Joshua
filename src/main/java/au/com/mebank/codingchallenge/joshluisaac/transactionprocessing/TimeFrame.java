package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TimeFrame {
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
