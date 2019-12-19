package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.web;

import au.com.mebank.codingchallenge.joshluisaac.Routes;
import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionHandler {

  private final TransactionService transactionService;

  @Autowired
  public TransactionHandler(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  /**
   * Creates a new transaction entry
   *
   * @param transactionRequest
   * @return
   */
  @PostMapping(value = Routes.Transactions.TRANSACTIONS)
  public ResponseEntity<Transaction> createTransaction(
      @RequestBody TransactionRequest transactionRequest) {
    Transaction transaction =
        Transaction.createPayment(
            transactionRequest.getFromAccountId(),
            transactionRequest.getToAccountId(),
            transactionRequest.getTransactionAmount());
    HttpHeaders httpHeaders = new HttpHeaders();
    return new ResponseEntity<>(transaction, httpHeaders, HttpStatus.CREATED);
  }

  @GetMapping(value = "/api/v1/transactions/{transactionId}")
  public ResponseEntity<Transaction> getTransaction(@PathVariable String transactionId) {
    Transaction transaction = transactionService.getTransaction(transactionId);
    return new ResponseEntity<>(transaction, HttpStatus.OK);
  }

  @GetMapping(value = "/api/v1/transactions")
  public ResponseEntity<String> searchTransactions(
      @RequestParam String account_id,
      @RequestParam String created_at,
      @RequestParam String sort,
      @RequestParam String amount,
      @RequestParam String page) {
    System.out.println(">> Account number: " + account_id);
    System.out.println(">>Transaction date: " + created_at);
    System.out.println(">>Sorting: " + sort);
    System.out.println(">> Amount " + amount);
    System.out.println(">>Pagination: " + page);
    // Transaction transaction = transactionService.getTransaction(transactionId);
    return new ResponseEntity<>("Search....", HttpStatus.OK);
  }

  @DeleteMapping(value = "/api/v1/transactions")
  public ResponseEntity<String> deleteTransaction(
      @RequestBody TransactionRequest transactionRequest) {
    System.out.println(transactionRequest.getFromAccountId());
    // Transaction transaction = transactionService.getTransaction(transactionId);
    return new ResponseEntity<>("Deleted...", HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/api/v1/transactions")
  public ResponseEntity<String> updateTransaction(
          @RequestBody TransactionRequest transactionRequest) {
    System.out.println(transactionRequest.getFromAccountId());
    // Transaction transaction = transactionService.getTransaction(transactionId);
    return new ResponseEntity<>("Updated..", HttpStatus.OK);
  }



  // accounts/:account_id/transactions
  // accounts/:account_id/transactions/transaction_id?credit=true
  // accounts/:account_id/transactions/transaction_id?credit=false

  // transactions?accountId=87669

  // http://localhost:8888/api/v1/transactions?account_id=XXXX&created_at=20102018T124755&sort=created_at:desc

  //http://localhost:8888/api/v1/transactions?account_id=XXXX&created_at=20102018T12:47:55&sort=created_at:desc&id={"lt": 100, "gt": 30}&page={"start": 1, "size": 10}
}
