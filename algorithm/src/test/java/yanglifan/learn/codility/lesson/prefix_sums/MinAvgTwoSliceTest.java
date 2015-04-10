package yanglifan.learn.codility.lesson.prefix_sums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MinAvgTwoSliceTest {
    MinAvgTwoSlice minAvgTwoSlice = new MinAvgTwoSlice();

    int[] array;
    int result;

    public MinAvgTwoSliceTest(int[] array, int result) {
        this.array = array;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{4, 2, 2, 5, 1, 5, 8}, 1},
                {new int[]{0, 0}, 0},
                {new int[]{1, 2, 0, 0, 1, 2, 0, 0}, 2},
                {new int[]{1, 2, 3, 4, 5}, 0},
                {new int[]{5, 4, 3, 2, 1}, 3}
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(minAvgTwoSlice.solution(array), is(result));
    }
}