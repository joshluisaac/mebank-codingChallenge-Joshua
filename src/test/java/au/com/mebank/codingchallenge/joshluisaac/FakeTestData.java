package au.com.mebank.codingchallenge.joshluisaac;

import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.Transaction;
import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionType;
import au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.TransactionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FakeTestData {

    public static List<Transaction> fakeDataSet() {
        Transaction tx10001 =
                Transaction.builder()
                        .transactionId("TX10001")
                        .fromAccountId("ACC334455")
                        .toAccountId("ACC778899")
                        .createdAt(TransactionUtils.parseDate("20/10/2018 12:47:55"))
                        .amount(new BigDecimal("25.00"))
                        .transactionType(TransactionType.PAYMENT)
                        .build();

        Transaction tx10002 =
                Transaction.builder()
                        .transactionId("TX10002")
                        .fromAccountId("ACC334455")
                        .toAccountId("ACC998877")
                        .createdAt(TransactionUtils.parseDate("20/10/2018 17:33:43"))
                        .amount(new BigDecimal("10.50"))
                        .transactionType(TransactionType.PAYMENT)
                        .build();

        Transaction tx10003 =
                Transaction.builder()
                        .transactionId("TX10003")
                        .fromAccountId("ACC998877")
                        .toAccountId("ACC778899")
                        .createdAt(TransactionUtils.parseDate("20/10/2018 18:00:00"))
                        .amount(new BigDecimal("5.00"))
                        .transactionType(TransactionType.PAYMENT)
                        .build();

        Transaction tx10004 =
                Transaction.builder()
                        .transactionId("TX10004")
                        .fromAccountId("ACC334455")
                        .toAccountId("ACC998877")
                        .createdAt(TransactionUtils.parseDate("20/10/2018 19:45:00"))
                        .amount(new BigDecimal("10.50"))
                        .transactionType(TransactionType.REVERSAL)
                        .relatedTransaction("TX10002")
                        .build();

        Transaction tx10005 =
                Transaction.builder()
                        .transactionId("TX10005")
                        .fromAccountId("ACC334455")
                        .toAccountId("ACC778899")
                        .createdAt(TransactionUtils.parseDate("21/10/2018 09:30:00"))
                        .amount(new BigDecimal("7.25"))
                        .build();

        List<Transaction> dataSet = new ArrayList<>();
        dataSet.add(tx10001);
        dataSet.add(tx10002);
        dataSet.add(tx10003);
        dataSet.add(tx10004);
        dataSet.add(tx10005);
        return dataSet;
    }


}
