# Some exceptions and recovery measures

- [Some exceptions and recovery measures](#some-exceptions-and-recovery-measures)
  - [AccountId cannot be blank or zero-length field](#accountid-cannot-be-blank-or-zero-length-field)
  - [from and to cannot be blank or zero-length field](#from-and-to-cannot-be-blank-or-zero-length-field)
  - [Invalid from or to dates](#invalid-from-or-to-dates)
  - [The application cannot find the CSV dataset file](#the-application-cannot-find-the-csv-dataset-file)
  - [AccountId, to &amp; from cannot be null](#accountid-to-amp-from-cannot-be-null)

## AccountId cannot be blank or zero-length field

```log
Exception in thread "main" java.lang.IllegalArgumentException: AccountId is either blank or empty. AccountId cannot be a zero-length string
        at com.google.common.base.Preconditions.checkArgument(Preconditions.java:142)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.jvmArgsPreconditions(FinancialTransactionApplication.java:60)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.mapJvmArgs(FinancialTransactionApplication.java:43)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.main(FinancialTransactionApplication.java:22)
```

## from and to cannot be blank or zero-length field

```log
Exception in thread "main" java.lang.IllegalArgumentException: from is either blank or empty. from cannot be blank or zero-length string
        at com.google.common.base.Preconditions.checkArgument(Preconditions.java:142)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.jvmArgsPreconditions(FinancialTransactionApplication.java:75)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.mapJvmArgs(FinancialTransactionApplication.java:51)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.invoke(FinancialTransactionApplication.java:33)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.main(FinancialTransactionApplication.java:28)
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

## The application cannot find the CSV dataset file

> Please check `-DcsvFile="sampleDataSet.csv"` argument

```log
Exception in thread "main" java.nio.file.NoSuchFileException: sampleDataSet.csvx
        at java.base/sun.nio.fs.UnixException.translateToIOException(UnixException.java:92)
        at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:111)
        at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:116)
        at java.base/sun.nio.fs.UnixFileSystemProvider.newFileChannel(UnixFileSystemProvider.java:178)
        at java.base/java.nio.channels.FileChannel.open(FileChannel.java:292)
        at java.base/java.nio.channels.FileChannel.open(FileChannel.java:345)
        at java.base/java.nio.file.Files.lines(Files.java:4033)
        at java.base/java.nio.file.Files.lines(Files.java:4125)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.createTransactionDataSet(FinancialTransactionApplication.java:112)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.invoke(FinancialTransactionApplication.java:36)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.main(FinancialTransactionApplication.java:28)
```

## AccountId, to & from cannot be null

> Please check `-DaccountId,-Dfrom and -Dto` JVM arguments

```log
Exception in thread "main" java.lang.NullPointerException: accountId is marked non-null but is null
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.jvmArgsPreconditions(FinancialTransactionApplication.java:68)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.mapJvmArgs(FinancialTransactionApplication.java:51)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.invoke(FinancialTransactionApplication.java:33)
        at au.com.mebank.codingchallenge.joshluisaac.FinancialTransactionApplication.main(FinancialTransactionApplication.java:28)
```
