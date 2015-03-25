package yanglifan.learn.java8.stream;

import yanglifan.learn.java8.stream.Transaction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lifan on 3/25/2015.
 */
public class TestUtils {
    public static List<Transaction> newTransactions() {
        return Arrays.asList(
                new Transaction(1, Transaction.TransactionType.GROCERY, new BigDecimal("10")),
                new Transaction(2, Transaction.TransactionType.GROCERY, new BigDecimal("20")),
                new Transaction(3, Transaction.TransactionType.SUPERMARKET, new BigDecimal("100")));
    }
}
