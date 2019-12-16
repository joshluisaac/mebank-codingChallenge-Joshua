package au.com.mebank.codingchallenge.joshluisaac;

import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.*;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinancialTransactionApplication {

  private static final Logger LOG = LoggerFactory.getLogger(FinancialTransactionApplication.class);

  private FinancialTransactionApplication() {}

  private static final String ACCOUNT_ID = "accountId";
  private static final String START_DATE = "from";
  private static final String END_DATE = "to";
  private static final String CSV_FILE_PATH = "csvFile";

  public static void main(String[] args) throws IOException {
    Result result = invoke();
    printResult(result);
  }

  public static Result invoke() throws IOException {
    final Map<String, String> jvmArgs = mapJvmArgs();
    final TransactionQueryScope queryScope = createTransactionQueryScope(jvmArgs);
    final ITransactions<Transaction> transactions =
        new Transactions(createTransactionDataSet(jvmArgs), queryScope);
    final AccountBalance accountBalance = new RelativeAccountBalance(transactions);
    return accountBalance.balance();
  }

  /**
   * Maps JVM arguments that was passed from command line.
   *
   * @return {@link Map} of JVM args.
   */
  private static Map<String, String> mapJvmArgs() {
    String accountId = System.getProperty(ACCOUNT_ID);
    String startDate = System.getProperty(START_DATE).trim();
    String endDate = System.getProperty(END_DATE).trim();
    String csvFilePath = System.getProperty(CSV_FILE_PATH).trim();
    jvmArgsPreconditions(accountId, startDate, endDate, csvFilePath);
    return Map.of(
        ACCOUNT_ID, accountId,
        START_DATE, startDate,
        END_DATE, endDate,
        CSV_FILE_PATH, csvFilePath);
  }

  /**
   * Validates command line arguments. Checking for empty, null or a zero length string
   *
   * @param accountId
   * @param startDate
   * @param endDate
   * @param csvFilePath
   */
  private static void jvmArgsPreconditions(
      @NonNull String accountId,
      @NonNull String startDate,
      @NonNull String endDate,
      @NonNull String csvFilePath) {
    Preconditions.checkArgument(
        !(accountId.isBlank() || accountId.isEmpty()),
        "AccountId is either blank or empty. Account id cannot be blank or zero-length string");
    Preconditions.checkArgument(
        !(startDate.isBlank() || startDate.isEmpty()),
        "from is either blank or empty. from cannot be blank or zero-length string");
    Preconditions.checkArgument(
        !(endDate.isBlank() || endDate.isEmpty()),
        "to is either blank or empty. to cannot be blank or zero-length string");
    Preconditions.checkArgument(
        !(csvFilePath.isBlank() || csvFilePath.isEmpty()),
        "csvFilePath is either blank or empty. csvFilePath cannot be blank or zero-length string");
  }

  /**
   * Takes the JVM arguments and builds a {@link TransactionQueryScope}
   *
   * @param jvmArgs
   * @return returns the specified {@link TransactionQueryScope}
   */
  private static TransactionQueryScope createTransactionQueryScope(Map<String, String> jvmArgs) {
    TimeFrame timeFrame =
        TimeFrame.builder()
            .startDate(TransactionUtils.parseDate(jvmArgs.get(START_DATE)))
            .endDate(TransactionUtils.parseDate(jvmArgs.get(END_DATE)))
            .build();
    return TransactionQueryScope.builder()
        .accountId(jvmArgs.get(ACCOUNT_ID))
        .timeFrame(timeFrame)
        .build();
  }

  /**
   * Takes the JVM arguments and builds a {@link List<Transaction>} of transactions
   *
   * @param jvmArgs
   * @return returns the specified {@link List<Transaction>} of transactions
   */
  private static List<Transaction> createTransactionDataSet(Map<String, String> jvmArgs)
      throws IOException {
    try (Stream<String> stream = Files.lines(Paths.get(jvmArgs.get(CSV_FILE_PATH)))) {
      return stream
          .map(TransactionUtils::parseCsvTransaction)
          .collect(Collectors.toUnmodifiableList());
    }
  }

  private static void printJvmArgs() {
    final Map<String, String> jvmArgs = mapJvmArgs();
    jvmArgs.forEach((k, v) -> System.out.println(">>> " + k + ": " + v));
  }

  private static void printResult(Result result) {
    System.out.println(String.format("%n"));
    LOG.info("Printing Command line arguments");
    printJvmArgs();
    System.out.println(String.format("%n"));
    LOG.info("Printing results");
    System.out.println(">>> Relative  balance for the period is: " + result.getBalance());
    System.out.println(
        ">>> Number of transactions included is: " + result.getTransactionsIncluded());
    System.out.println(String.format("%n"));
  }
}
