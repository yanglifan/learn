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
        Optional<Transaction> optional = newTransactions().stream()
                .filter(t -> t.getType() == Transaction.TransactionType.GROCERY)
                .findAny();

        assertThat(optional.get().getId(), is(1));
    }

    @Test
    public void word_length() {
        List<String> words = Arrays.asList("Oracle", "Java", "Magazine");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        assertThat(wordLengths, is(Arrays.asList(6, 4, 8)));
    }

    @Test
    public void reduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        assertThat(sum, is(6));

        int max = numbers.stream().reduce(2, Integer::max);
        assertThat(max, is(3));
    }

    private List<Transaction> newTransactions() {
        return Arrays.asList(
                new Transaction(1, Transaction.TransactionType.GROCERY, new BigDecimal("10")),
                new Transaction(2, Transaction.TransactionType.GROCERY, new BigDecimal("20")),
                new Transaction(3, Transaction.TransactionType.SUPERMARKET, new BigDecimal("100")));
    }
}
