package yanglifan.learn.java8.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestUtils {
    public static List<Transaction> newTransactions() {
        return Arrays.asList(
                new Transaction(1, Transaction.TransactionType.GROCERY, new BigDecimal("10")),
                new Transaction(2, Transaction.TransactionType.GROCERY, new BigDecimal("20")),
                new Transaction(3, Transaction.TransactionType.SUPERMARKET, new BigDecimal("100")));
    }

    public static List<Transaction> newBigGroceryTransactions() {
        return IntStream
                .range(1, 10_000)
                .boxed()
                .map(i -> new Transaction(i, Transaction.TransactionType.GROCERY, new BigDecimal(10)))
                .collect(toList());
    }

    public static Collection<Shape> newEmptyShapes() {
        return Arrays.asList(new Shape(), new Shape(), new Shape());
    }
}
