package yanglifan.learn.algorithm.codility.lesson.sorting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class SortTest {
    int[] source, result;

    public SortTest(int[] source, int[] result) {
        this.source = source;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        int end = 100_000;
        int[] bigIntArray = IntStream.range(0, end).map(i -> end - i - 1).toArray();
        int[] bigIntResult = IntStream.range(0, end).toArray();

        return Arrays.asList(new Object[][]{
                {new int[]{6, 11, 2, 3}, new int[]{2, 3, 6, 11}},
                {new int[]{0, 1, 2, 3}, new int[]{0, 1, 2, 3}},
                {new int[]{3, 2, 1, 0}, new int[]{0, 1, 2, 3}},
                {new int[]{0}, new int[]{0}},
                {bigIntArray, bigIntResult}
        });
    }

    @Test
    public void testQuickSort() throws Exception {
        int[] r = Sort.quickSort(source);
        assertThat(r, is(result));
    }

    @Test
    public void testHeapSort() {
        int[] r = Sort.heapSort(source);
        assertThat(r, is(result));
    }
}
