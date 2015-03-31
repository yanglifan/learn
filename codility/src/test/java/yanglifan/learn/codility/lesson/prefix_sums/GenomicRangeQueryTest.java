package yanglifan.learn.codility.lesson.prefix_sums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GenomicRangeQueryTest {
    String s;
    int[] p;
    int[] q;
    int[] result;

    public GenomicRangeQueryTest(String s, int[] p, int[] q, int[] result) {
        this.s = s;
        this.p = p;
        this.q = q;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"CAGCCTA", new int[]{2, 5, 0}, new int[]{4, 5, 6}, new int[]{2, 4, 1}},
                {"AAAAAA", new int[]{0, 1, 2, 3}, new int[]{2, 3, 4, 5}, new int[]{1, 1, 1, 1}},
                {"ACGT", new int[]{0, 1, 2, 3}, new int[]{0, 1, 2, 3}, new int[]{1, 2, 3, 4}},
                {"A", new int[]{0}, new int[]{0}, new int[]{1}},
                {"C", new int[]{0}, new int[]{0}, new int[]{2}},
                {"G", new int[]{0}, new int[]{0}, new int[]{3}},
                {"T", new int[]{0}, new int[]{0}, new int[]{4}}
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(GenomicRangeQuery.solution(s, p, q), is(result));
    }
}