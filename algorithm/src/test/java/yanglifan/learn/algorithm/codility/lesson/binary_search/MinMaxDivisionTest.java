package yanglifan.learn.algorithm.codility.lesson.binary_search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class MinMaxDivisionTest {

    int k;
    int m;
    int[] a;
    int r;

    public MinMaxDivisionTest(int k, int m, int[] a, int r) {
        this.k = k;
        this.m = m;
        this.a = a;
        this.r = r;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {3, 5, new int[]{2, 1, 5, 1, 2, 2, 2}, 6},
                {3, 5, new int[]{1, 1, 1, 1, 1, 1}, 2}
        });
    }

    @Test
    public void testSolution() throws Exception {
        System.out.println("========");
        assertThat(MinMaxDivision.solution(k, m, a), is(r));
    }
}