package yanglifan.learn.java8.stream;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    /**
     * Performance better than normal stream?
     */
    @Test
    public void int_stream() {
        int sum = newTransactions().stream().mapToInt(t -> t.getValue().intValue()).sum();
        assertThat(sum, is(130));
    }

    @Test
    public void get_odd_numbers() {
        List<Integer> odds = IntStream.rangeClosed(10, 20).filter(i -> i % 2 == 1).boxed().collect(Collectors.toList());
        assertThat(odds, is(Arrays.asList(11, 13, 15, 17, 19)));
    }

    @Test
    public void build_stream() {
        Stream<Integer> streamFromNumbers = Stream.of(1, 2, 3, 4);
        IntStream streamFromArray = Arrays.stream(new int[]{1, 2, 3, 4});
    }

    @Test
    public void count_file_line_number() throws IOException {
        String myfileFullPath = getClass().getClassLoader().getResource("myfile.txt").getPath();
        long numOfLines = Files.lines(Paths.get(myfileFullPath)).count();
        assertThat(numOfLines, is(6L));
    }

    @Test
    public void infinite_iterate() {
        Stream<Integer> numbers = Stream.iterate(0, n -> n + 10);
        List<Integer> tenNumbers = numbers.limit(5).collect(Collectors.toList());
        assertThat(tenNumbers, is(Arrays.asList(0, 10, 20, 30, 40)));
    }

    private List<Transaction> newTransactions() {
        return Arrays.asList(
                new Transaction(1, Transaction.TransactionType.GROCERY, new BigDecimal("10")),
                new Transaction(2, Transaction.TransactionType.GROCERY, new BigDecimal("20")),
                new Transaction(3, Transaction.TransactionType.SUPERMARKET, new BigDecimal("100")));
    }
}
