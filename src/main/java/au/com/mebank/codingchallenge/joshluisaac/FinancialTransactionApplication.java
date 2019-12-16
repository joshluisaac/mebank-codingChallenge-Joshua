package au.com.mebank.codingchallenge.joshluisaac;

import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TimeFrame;
import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionQueryScope;
import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionUtils;
import java.time.LocalDateTime;
import java.util.Map;

public class FinancialTransactionApplication {

  public static void main(String[] args) {
    // processJvmArgs();

    Map<String, String> jvmArgs = getJvmArgs();

    TransactionQueryScope queryScope = createTransactionQueryScope(jvmArgs);
    System.out.println(queryScope.getAccountId());
  }

  static Map<String, String> getJvmArgs() {
    return Map.of(
        "accountId",
        System.getProperty("accountId"),
        "from",
        System.getProperty("from").trim(),
        "to",
        System.getProperty("to").trim());
  }

  static TransactionQueryScope createTransactionQueryScope(Map<String, String> jvmArgs) {
    TimeFrame timeFrame =
        TimeFrame.builder()
            .startDate(checkTimeFrame(jvmArgs.get("from")))
            .endDate(checkTimeFrame(jvmArgs.get("to")))
            .build();
    return TransactionQueryScope.builder().accountId(jvmArgs.get("accountId")).timeFrame(timeFrame).build();
  }

  static LocalDateTime checkTimeFrame(String rawText) {
    return TransactionUtils.parseDate(rawText);
  }

  void getTimeFrame() {}
}
