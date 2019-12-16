[![Build Status](https://travis-ci.org/joshluisaac/mebank-codingChallenge-Joshua.svg?branch=master)](https://travis-ci.org/joshluisaac/mebank-codingChallenge-Joshua)
[![Coverage Status](https://coveralls.io/repos/github/joshluisaac/mebank-codingChallenge-Joshua/badge.svg)](https://coveralls.io/github/joshluisaac/mebank-codingChallenge-Joshua)


# Transaction Processing Application

This is a transaction processing application which provides a set of features
covering the specification of the requirements described [here](meBankCodingChallenge.pdf).

The application calculates and logs to terminal/console the relative account balance for a group of account transactions within a stipulated time frame
and the number of transactions that are included.

# Table of Contents

* [Application design and some design decisions](#application-design-and-some-design-decisions)
* [Prerequisites](#prerequisites)
* [Running the test suite](#running-the-test-suite)
* [Building the source](#building-the-source)
* [Running the app from terminal](#running-the-app-from-terminal)
* [Code coverage](#code-coverage)
* [Code formatting](#code-formatting)







## Application design and some design decisions
A class diagram showing how the various pieces and components fits together can be found [here](screenshots/classDiagram.png).
Public interface methods to the system contains code documentation describing the operation.

                                                                                   
### RelativeAccountBalance
[RelativeAccountBalance](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/RelativeAccountBalance.java) 
implements [AccountBalance](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/AccountBalance.java) interface and it's responsibility is to __collate/compute the total of credit and debit transactions__. It has a `balance()` method which returns a 
[Result](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/Result.java). 

Invoking `Result.balance()` returns the relative balance while `Result.transactionsIncluded()` returns the number of transactions included.

This object has the following invariant. For it to be in a valid state, transactions must be `NonNull`

```java
  public RelativeAccountBalance(@NonNull ITransactions<Transaction> transactions) {
    this.transactions = transactions;
  }
```

### Transactions
A [Transactions](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/Transactions.java) object is the representation of the list of transaction entries. It implements 
`ITransactions<Transaction>` and has operations for retrieving credit and debit transactions.
Each entry is represented as a [Transaction](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/Transaction.java).

For the transactions object to be in a valid state, it must satisfy the following invariants 

1.  `Transaction scope` and input `transactionDataSet` cannot be null.
2. `transactionDataSet` cannot be empty.

Transactions invariants and preconditions
```java
  public Transactions(
      @NonNull List<Transaction> transactionDataSet, @NonNull TransactionQueryScope queryScope) {
    this.transactionDataSet = transactionDataSet;
    this.queryScope = queryScope;
    Preconditions.checkArgument(!transactionDataSet.isEmpty(), "Transaction dataset is empty");
  }
```

### TransactionQueryScope
A [TransactionQueryScope](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/TransactionQueryScope.java) is the notion of a group of related account transactions that were created within a 
given [TimeFrame](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/TimeFrame.java).
This object is then used to query those accounts that fall within that scope. 

### TransactionType
[TransactionType](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/TransactionType.java) is represented as an enum of either `PAYMENT` or `REVERSAL`

```java
public enum TransactionType {
  PAYMENT,
  REVERSAL;
}
```

### Credit & debit transactions
Every transaction is 2-phased. Money flows out on one end and money is received at the other end.

__Credit transactions__ are transactions in which money flows into an account. These may also be referred to as Accounts receivable (AR). 
It uses `Transaction.getToAccountId()` to identify these cases.

__Debit transactions__ are transactions in which money flows out of an account. These may also be referred to as Accounts payable (AP).
It uses `Transaction.getFromAccountId()` to identify these cases.


## Prerequisites

- JDK 11+ or higher
- Maven

## Running the test suite

Running this command will compile as well as run all tests

```bash
mvn compile test
```

Executing this command will yield the following console output

```log
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.CreditTransactionsTestCase
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.171 s - in au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.CreditTransactionsTestCase
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TimeFrameTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 s - in au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TimeFrameTest
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionsInvariantsTestCase
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 s - in au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionsInvariantsTestCase
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.DebitTransactionsTestCase
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s - in au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.DebitTransactionsTestCase
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionUtilsTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.043 s - in au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionUtilsTest
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 s - in au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionTest
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.RelativeAccountBalanceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 s - in au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.RelativeAccountBalanceTest
[INFO] Running au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplicationTest
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.021 s - in au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplicationTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 55, Failures: 0, Errors: 0, Skipped: 0

```

## Building the source

This will download all the required dependencies and create an executable JAR file in the target directory.
The executable JAR was created using [Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/)

```bash
mvn clean install
```

## Running the app from terminal

Execute the below command to build and execute the app from terminal.

```bash
mvn clean install && java -jar -DaccountId="ACC334455" -Dfrom="20/10/2018 12:00:00" -Dto="20/10/2018 19:00:00" -DcsvFile="sampleDataSet.csv" target/mebank-codingChallenge-Joshua-0.0.1-SNAPSHOT.jar
```
or the following command to execute the application alone after building

```bash
java -jar -DaccountId="ACC334455" -Dfrom="20/10/2018 12:00:00" -Dto="20/10/2018 19:00:00" -DcsvFile="sampleDataSet.csv" target/mebank-codingChallenge-Joshua-0.0.1-SNAPSHOT.jar
```

#### JVM/Command-line arguments
* AccountId: `-DaccountId="ACC334455"`
  > The accountId that would be used to query the transactions data set.
* Start Date: `-Dfrom="20/10/2018 12:00:00"`
  > Transaction start date or start of time frame.
* End Date: `-Dto="20/10/2018 19:00:00"`
  > Transaction end date or end of time frame. Must be on or after start date.
* CSV File Path: `-DcsvFile="sampleDataSet.csv"`
  > Transaction data set.

Executing the above command will produce this output
```log
Relative  balance for the period is: -25.00
Number of transactions included is: 1
```

## Code coverage

### Jacoco code coverage
While the goal of the test harness is to cover as much edge and corner cases, that naturally led to a wider coverage of over 90%.
Code coverage was both executed as part of maven build cycle using [JaCoCo](https://github.com/jacoco/jacoco)  and from IDE

![alt text][codeCoverageJacoco]
![alt text][codecoverage]

### Coverall report
Executing the following command will generate Jacoco and [coveralls coverage reports](https://coveralls.io/jobs/56914384
).
```bash
mvn clean test jacoco:report coveralls:report
```
![alt text][coverallReport]


## Code formatting
Source code was formatted using [google-java-format](https://github.com/google/google-java-format)



[codecoverage]: screenshots/codeCoverage_Ide.png "codeCoverage_Ide"
[codeCoverageJacoco]: screenshots/codeCoverageJacoco.png "codeCoverageJacoco"
[coverallReport]: screenshots/coverallReport.png "coverallReport"



