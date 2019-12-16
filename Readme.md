
# Transaction Processing Application

This is a transaction processing application which provides a set of features
covering the specification of the requirements described [here](meBankCodingChallenge.pdf).

The application calculates and logs to terminal/console the relative account balance for a group account transactions within a stipulated time frame
and the number of transactions that are included.

A class diagram showing how the various pieces fits together can be found [here](screenshots/classDiagram.png)


```bash
java -jar -DaccountId="ACC334455" -Dfrom="20/10/2018 12:00:00" -Dto="20/10/2018 19:00:00" 
target/mebank-codingChallenge-Joshua-0.0.1-SNAPSHOT.jar
```

### What is a TransactionQueryScope?
A [TransactionQueryScope](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/TransactionQueryScope.java) is the notion of a group of related account transactions that were created within a 
given [TimeFrame](src/main/java/au/com/mebank/codingchallenge/joshluisaac/transactionprocessing/TimeFrame.java).
This object is then used to query those accounts that fall within that scope. 



## Code formatting