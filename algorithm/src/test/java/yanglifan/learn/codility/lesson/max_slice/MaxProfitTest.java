package yanglifan.learn.codility.lesson.max_slice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class MaxProfitTest {

    int[] a;
    int r;

    public MaxProfitTest(int[] a, int r) {
        this.a = a;
        this.r = r;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{23171, 21011, 21123, 21366, 21013, 21367}, 356},
                {new int[]{1000, 2000, 3000, 4000, 3000, 5000}, 4000},
                {new int[]{0}, 0},
                {new int[]{1, 1, 1}, 0},
                {new int[]{}, 0},
                {new int[]{1, 2, 3, 4}, 3}

        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(MaxProfit.solution(a), is(r));
    }
}