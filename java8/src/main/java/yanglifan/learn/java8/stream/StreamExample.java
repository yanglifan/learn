package yanglifan.learn.java8.stream;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamExample {
    @Test
    public void test_two_even_squares() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> twoEvenSquares = numbers.stream()
                .filter(n -> {
                    System.out.println("filtering " + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    System.out.println("mapping " + n);
                    return n * n;
                })
                .limit(2)
                .collect(Collectors.toList());
        assertThat(twoEvenSquares, is(Arrays.asList(4, 16)));
    }

    @Test
    public void find_any_grocery() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1, Transaction.TransactionType.GROCERY, new BigDecimal("10")),
                new Transaction(2, Transaction.TransactionType.GROCERY, new BigDecimal("20")),
                new Transaction(3, Transaction.TransactionType.SUPERMARKET, new BigDecimal("100")));

        Optional<Transaction> optional = transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.GROCERY)
                .findAny();

        assertThat(optional.get().getId(), is(1));
    }
}
