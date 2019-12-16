
# Transaction Processing Application

This is a transaction processing application which provides a set of features
covering the specification of the requirements described [here](meBankCodingChallenge.pdf).

The application calculates and logs to terminal/console the relative account balance for a group account transactions within a stipulated time frame
and the number of transactions that are included.

A class diagram showing how the various pieces fits together can be found [here](screenshots/classDiagram.png)


### What is a TransactionQueryScope?
A [TransactionQueryScope](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/TransactionQueryScope.java) is the notion of a group of related account transactions that were created within a 
given [TimeFrame](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/TimeFrame.java).
This object is then used to query those accounts that fall within that scope. 

### Transactions
A [Transactions](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/Transactions.java) object is the representation of the list of transaction entries. 
Each entry is represented as a [Transaction](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/Transaction.java) 
For the transactions object to be in a valid state, it must satisfy the following invariants 

*  `Transaction scope` and input `transactionDataSet` cannot be null.
*  `transactionDataSet` cannot be empty.

## Prerequisites

- JDK 11+ or higher
- Maven

## Running the test suite

Running this command will compile as well as run all tests

```bash
mvn compile test
```

## Building the source

This will download all the required dependencies and create an executable JAR file.

```bash
mvn clean install
```

## Running the app from terminal

Execute the below command to build and execute the app from terminal.

```bash
mvn clean install && java -jar -DaccountId="ACC334455" -Dfrom="20/10/2018 12:00:00" -Dto="20/10/2018 19:00:00" -DcsvFile="sampleDataSet.csv" target/mebank-codingChallenge-Joshua-0.0.1-SNAPSHOT.jar
```

#### JVM arguments
* AccountId: `-DaccountId="ACC334455"`
* Start Date: `-Dfrom="20/10/2018 12:00:00"`
* End Date: `-Dto="20/10/2018 19:00:00"`
* CSV File Path: `-DcsvFile="sampleDataSet.csv"`

Executing the above command will produce this output
```bash
Relative  balance for the period is: -25.00
Number of transactions included is: 1
```

## Code coverage

While the goal of the test harness is to cover as much edge and corner cases, that naturally led to a wider coverage of over 90%.
Code coverage was both executed as part of maven build cycle using [JaCoCo](https://github.com/jacoco/jacoco)  and from IDE

![alt text][codeCoverageJacoco]
![alt text][codecoverage]

## Code formatting
Source code was formatted using [google-java-format](https://github.com/google/google-java-format)

[codecoverage]: screenshots/codeCoverage_Ide.png "codeCoverage_Ide"
[codeCoverageJacoco]: screenshots/codeCoverageJacoco.png "codeCoverageJacoco"


