package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransactionQueryScope {
  private String accountId;
  private TimeFrame timeFrame;
}
