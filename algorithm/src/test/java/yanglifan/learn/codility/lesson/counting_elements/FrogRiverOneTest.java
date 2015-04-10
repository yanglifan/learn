package yanglifan.learn.codility.lesson.counting_elements;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class FrogRiverOneTest {
    private FrogRiverOne frogRiverOne = new FrogRiverOne();
    private int x;
    private int[] input;
    private int result;

    public FrogRiverOneTest(int x, int[] input, int result) {
        this.x = x;
        this.input = input;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {5, new int[]{1, 3, 1, 4, 2, 3, 5, 4}, 6},
                {1, new int[]{1}, 0},
                {1, new int[]{1, 2}, 0},
                {2, new int[]{1, 2}, 1}
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(frogRiverOne.solution(x, input), is(result));
    }
}