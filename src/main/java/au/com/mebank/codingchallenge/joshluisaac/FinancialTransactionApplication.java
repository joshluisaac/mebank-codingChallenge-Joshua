package au.com.mebank.codingchallenge.joshluisaac;

import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FinancialTransactionApplication {

  private static final String ACCOUNT_ID = "accountId";
  private static final String START_DATE = "from";
  private static final String END_DATE = "to";
  private static final String CSV_FILE_PATH = "csvFile";

  public static void main(String[] args) throws IOException {
    final Map<String, String> jvmArgs = mapJvmArgs();
    final TransactionQueryScope queryScope = createTransactionQueryScope(jvmArgs);
    final ITransactions<Transaction> transactions =
        new Transactions(createTransactionDataSet(jvmArgs), queryScope);
    final AccountBalance accountBalance = new RelativeAccountBalance(transactions);
    final Result result = accountBalance.balance();
    System.out.println("Relative  balance for the period is: " + result.getBalance());
    System.out.println("Number of transactions included is: " + result.getTransactionsIncluded());
  }

  static Map<String, String> mapJvmArgs() {
    return Map.of(
        ACCOUNT_ID,
        System.getProperty(ACCOUNT_ID),
        START_DATE,
        System.getProperty(START_DATE).trim(),
        END_DATE,
        System.getProperty(END_DATE).trim(),
        CSV_FILE_PATH,
        System.getProperty(CSV_FILE_PATH).trim());
  }

  void jvmArgsPreconditions() {
    // input args cannot be empty, null or a zero length string
  }

  static TransactionQueryScope createTransactionQueryScope(Map<String, String> jvmArgs) {
    TimeFrame timeFrame =
        TimeFrame.builder()
            .startDate(checkTimeFrame(jvmArgs.get(START_DATE)))
            .endDate(checkTimeFrame(jvmArgs.get(END_DATE)))
            .build();
    return TransactionQueryScope.builder()
        .accountId(jvmArgs.get(ACCOUNT_ID))
        .timeFrame(timeFrame)
        .build();
  }

  static List<Transaction> createTransactionDataSet(Map<String, String> jvmArgs)
      throws IOException {
    try (Stream<String> stream = Files.lines(Paths.get(jvmArgs.get(CSV_FILE_PATH)))) {
      return stream
          .map(TransactionUtils::parseCsvTransaction)
          .collect(Collectors.toUnmodifiableList());
    }
  }

  static LocalDateTime checkTimeFrame(String rawText) {
    return TransactionUtils.parseDate(rawText);
  }
}
