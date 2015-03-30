package yanglifan.learn.codility.lesson.prefix_sums;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CountDivTest {

    CountDiv countDiv;

    int a, b, k, r;

    @Before
    public void setUp() throws Exception {
        countDiv = new CountDiv();
    }

    public CountDivTest(int a, int b, int k, int r) {
        this.a = a;
        this.b = b;
        this.k = k;
        this.r = r;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {6, 11, 2, 3},
                {6, 6, 2, 1},
                {7, 7, 2, 0},
                {0, 3, 4, 1},
                {1, 3, 4, 0},
                {1, 20_000_000, 19_000_000, 1}
        });
    }

    @Test
    public void testSolution() throws Exception {
        int result = countDiv.solution(a, b, k);
        assertThat(result, is(r));
    }
}