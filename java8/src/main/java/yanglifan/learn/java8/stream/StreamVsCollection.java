package yanglifan.learn.java8.stream;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class StreamVsCollection {
    private List<Transaction> transactions;

    @Before
    public void setUp() throws Exception {
        transactions = Arrays.asList(
                new Transaction(1, Transaction.TransactionType.GROCERY, new BigDecimal("10")),
                new Transaction(2, Transaction.TransactionType.GROCERY, new BigDecimal("20")),
                new Transaction(3, Transaction.TransactionType.SUPERMARKET, new BigDecimal("100"))
        );
    }

    /**
     * select * from transactions where type = 'GROCERY'
     */
    @Test
    public void test_collection_way() {
        System.out.println("========== Collection Way ==========");
        List<Transaction> groceryTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getType() == Transaction.TransactionType.GROCERY) {
                groceryTransactions.add(t);
            }
        }
        Collections.sort(groceryTransactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                return t2.getValue().compareTo(t1.getValue());
            }
        });
        List<Integer> transactionIds = new ArrayList<>();
        for (Transaction t : groceryTransactions) {
            transactionIds.add(t.getId());
        }

        for (int id : transactionIds) {
            System.out.println(id);
        }
    }

    @Test
    public void stream_way() {
        System.out.println("========== Stream Way ==========");
        transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.GROCERY)
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .map(Transaction::getId)
                .forEach(System.out::println);
    }
}
