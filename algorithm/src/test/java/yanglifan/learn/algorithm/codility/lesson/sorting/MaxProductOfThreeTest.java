package yanglifan.learn.algorithm.codility.lesson.sorting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MaxProductOfThreeTest {
    int[] array;
    int result;

    public MaxProductOfThreeTest(int[] array, int result) {
        this.array = array;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{-3, 1, 2, -2, 5, 6}, 60},
                {new int[]{1, 2, 3}, 6},
                {new int[]{1, 1, 1}, 1},
                {new int[]{10, 10, 10}, 1000},
                {new int[]{10, 20, 30, -10, -20, -30}, 18000},
                {new int[]{1, 2, 3, 4, 5, 6}, 120},
                {new int[]{-1, -2, 1, 2}, 4},
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(MaxProductOfThree.solution(array), is(result));
    }
}