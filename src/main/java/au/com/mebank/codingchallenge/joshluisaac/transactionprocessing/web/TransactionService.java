package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.web;

import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.Transaction;
import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Value("file:sampleDataSet.csv")
  private Resource resource;

  private List<Transaction> transactions;

  @PostConstruct
  public void loadTransactions() throws IOException {
    try (Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()))) {
      transactions =
          stream
              .map(TransactionUtils::parseCsvTransaction)
              .collect(Collectors.toUnmodifiableList());
    }
  }

  public Transaction getTransaction(String transactionId) {
    return transactions
        .stream()
        .filter(entry -> entry.getTransactionId().equals(transactionId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Records not found"));
  }
}
