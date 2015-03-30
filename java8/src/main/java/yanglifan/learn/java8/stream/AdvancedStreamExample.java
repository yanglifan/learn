package yanglifan.learn.java8.stream;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AdvancedStreamExample {
    @Test
    public void flat_map() throws Exception {
        String filePath = getClass().getClassLoader().getResource("stuff.txt").getPath();
        List<String> distinctElements = Files.lines(Paths.get(filePath)) // Stream<String>
                .map(line -> line.split("\\s")) // Stream<String[]> Stream<String>
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        assertThat(distinctElements, is(Arrays.asList("aaa", "bbb", "ccc", "ddd")));

        List<String> distinctElements2 = Files.lines(Paths.get(filePath))
                .flatMap(line -> {
                    String[] words = line.split("\\s");
                    return Arrays.stream(words);
                })
                .distinct()
                .collect(Collectors.toList());
        assertThat(distinctElements2, is(Arrays.asList("aaa", "bbb", "ccc", "ddd")));
    }

    @Test
    public void group_by() {
        List<Transaction> transactions = TestUtils.newTransactions();
        Map<Transaction.TransactionType, List<Transaction>> tranMap =
                transactions.stream().collect(Collectors.groupingBy(Transaction::getType));
        assertThat(tranMap.get(Transaction.TransactionType.GROCERY).size(), is(2));
    }

    @Test
    public void parallelism() {
        List<Transaction> transactions = TestUtils.newBigGroceryTransactions();
        double average = transactions
                .parallelStream()
                .filter(t -> {
                    System.out.println(Thread.currentThread().getName());
                    return t.getType() == Transaction.TransactionType.GROCERY;
                })
                .mapToInt(t -> t.getValue().intValue())
                .average()
                .getAsDouble();
        assertThat(average, is(10D));
    }
}
