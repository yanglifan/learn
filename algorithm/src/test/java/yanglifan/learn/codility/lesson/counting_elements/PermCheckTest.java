package yanglifan.learn.codility.lesson.counting_elements;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class PermCheckTest {
    private PermCheck permCheck = new PermCheck();
    private int[] input;
    private int result;

    public PermCheckTest(int[] input, int result) {
        this.input = input;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{4, 3, 1, 2}, 1},
                {new int[]{1, 2, 4}, 0},
                {new int[]{4, 1, 3}, 0},
                {new int[]{1, 1}, 0},
                {new int[]{1}, 1},
                {new int[]{2}, 0}
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(permCheck.solution(input), is(result));
    }
}