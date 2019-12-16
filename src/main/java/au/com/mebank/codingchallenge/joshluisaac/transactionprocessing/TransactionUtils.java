package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing;

import com.google.common.base.Splitter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionUtils {

  private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern(DATE_FORMAT);

  private TransactionUtils() {}

  public static Transaction parseCsvTransaction(String rawText) {
    List<String> tokens = null;
    tokens = Splitter.on(',').splitToList(rawText);
    Transaction.TransactionBuilder transactionBuilder =
        Transaction.builder()
            .transactionId(tokens.get(0).trim())
            .fromAccountId(tokens.get(1).trim())
            .toAccountId(tokens.get(2).trim())
            .createdAt(parseDate(tokens.get(3).trim()))
            .amount(new BigDecimal(tokens.get(4).trim()))
            .transactionType(TransactionType.valueOf(tokens.get(5).trim()));
    if (tokens.size() >= 7) {
      transactionBuilder.relatedTransaction(tokens.get(6).trim());
    }
    return transactionBuilder.build();
  }

  public static LocalDateTime parseDate(String rawDate) {
    try {
      return LocalDateTime.parse(rawDate, DATE_TIME_FORMATTER);
    } catch (RuntimeException ex) {
      throw new IllegalArgumentException(String.format("%s is an invalid datetime format. %n Please use the following format (%s)", rawDate, DATE_FORMAT), ex);
    }

  }
}
