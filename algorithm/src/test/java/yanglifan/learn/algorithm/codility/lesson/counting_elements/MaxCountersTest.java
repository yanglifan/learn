package yanglifan.learn.algorithm.codility.lesson.counting_elements;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class MaxCountersTest {
    private MaxCounters maxCounters = new MaxCounters();
    private int n;
    private int[] a;
    private int[] result;

    public MaxCountersTest(int n, int[] a, int[] result) {
        this.n = n;
        this.a = a;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {5, new int[]{3, 4, 4, 6, 1, 4, 4}, new int[]{3, 2, 2, 4, 2}},
                {1, new int[]{1}, new int[]{1}},
                {1, new int[]{10}, new int[]{0}}

        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(maxCounters.solution(n, a), is(result));
    }
}