package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.web;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionRequest {

  private String fromAccountId;
  private String toAccountId;
  private BigDecimal transactionAmount;
}
