package yanglifan.learn.algorithm.codility.lesson.time_complexity;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class FrogJmpTest {
    private int x;
    private int y;
    private int d;
    private int result;
    public FrogJmpTest(int x, int y, int d, int result) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 1, 1, 0},
                {1, 1_000_000_000, 1, 999_999_999},
                {10, 85, 30, 3}
        });
    }

    @Test
    public void test() {
        FrogJmp frogJmp = new FrogJmp();
        assertThat(frogJmp.solution(x, y, d), is(result));
    }
}