# Some exceptions and recovery measures

## AccountId cannot be blank or zero-length field
``` 
Exception in thread "main" java.lang.IllegalArgumentException: AccountId is either blank or empty. AccountId cannot be a zero-length string
        at com.google.common.base.Preconditions.checkArgument(Preconditions.java:142)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.jvmArgsPreconditions(FinancialTransactionApplication.java:60)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.mapJvmArgs(FinancialTransactionApplication.java:43)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.main(FinancialTransactionApplication.java:22)
```

## Invalid from or to dates
> Date must be in the following format `dd/MM/yyyy HH:mm:ss` For example, `20/10/2018 19:00:00`
```log
Exception in thread "main" java.lang.IllegalArgumentException: 20/10/2018 19:00:0 is an invalid datetime format. 
 Please use the following format (dd/MM/yyyy HH:mm:ss)
        at au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionUtils.parseDate(TransactionUtils.java:38)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.createTransactionQueryScope(FinancialTransactionApplication.java:96)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.invoke(FinancialTransactionApplication.java:34)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.main(FinancialTransactionApplication.java:28)
Caused by: java.time.format.DateTimeParseException: Text '20/10/2018 19:00:0' could not be parsed at index 17
        at java.base/java.time.format.DateTimeFormatter.parseResolved0(DateTimeFormatter.java:2046)
        at java.base/java.time.format.DateTimeFormatter.parse(DateTimeFormatter.java:1948)
        at java.base/java.time.LocalDateTime.parse(LocalDateTime.java:492)
        at au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionUtils.parseDate(TransactionUtils.java:36)


```