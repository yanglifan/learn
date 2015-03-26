package yanglifan.learn.core;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TopKHandlerTest {
    @Test
    public void simple_test_1() {
        System.out.println("========== simple_test_1 ==========");
        int[] source = new int[]{1, 2, 3, 4, 5, 6};
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.solve(2);
        assertThat(topK, is(new Integer[]{6, 5}));
    }

    @Test
    public void simple_test_2() {
        System.out.println("========== simple_test_2 ==========");
        int[] source = new int[]{0, 0, 0, 0, 0};
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.solve(2);
        assertThat(topK, is(new Integer[]{0, 0}));
    }

    @Test
    public void simple_test_3() {
        System.out.println("========== simple_test_3 ==========");
        int[] source = new int[]{1, 5, 18, -4, 99, 3, 0, 67, 1, 2, 11};
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.solve(3);
        assertThat(topK, is(new Integer[]{99, 67, 18}));
    }

    @Test
    public void test_random_array() {
        System.out.println("========== main_test ==========");
        int[] source = randomArray(10_000_000);
        long start = System.currentTimeMillis();
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.solve(100);
        System.out.println("Time spend is " + (System.currentTimeMillis() - start) / 1000 + " s");
        printResult(topK);
    }

    /**
     * Asc array is the worst condition.
     *
     * If do not consider duplicate element, the time spend is less than 3s.
     * 4 cores vm.
     */
    @Test
    public void test_order_array() {
        System.out.println("========== test_order_array ==========");
        int[] source = orderArray(10_000_000);
        long start = System.currentTimeMillis();
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.solve(100);
        System.out.println("Time spend is " + (System.currentTimeMillis() - start) / 1000 + " s");
        printResult(topK);

        assert topK[0] == 9_999_999;
        assert topK[99] == 9_999_900;
    }

    private void printResult(Integer[] topK) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < topK.length; i++) {
            builder.append(topK[i]);
            if (i != 99) {
                builder.append(", ");
            }
        }

        System.out.println("Test Order Array result is " + builder.toString());
    }

    private int[] randomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            int a = random.nextInt(10_000_000);
            array[i] = a;
        }

        return array;
    }

    private int[] orderArray(int size) {
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        return array;
    }
}