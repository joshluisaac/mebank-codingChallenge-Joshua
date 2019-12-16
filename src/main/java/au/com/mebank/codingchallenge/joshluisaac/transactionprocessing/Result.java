package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Result {

  private BigDecimal balance;
  private int transactionsIncluded;
}
