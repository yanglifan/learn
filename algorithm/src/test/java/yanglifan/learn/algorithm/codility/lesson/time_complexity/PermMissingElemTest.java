package yanglifan.learn.algorithm.codility.lesson.time_complexity;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class PermMissingElemTest {
    private PermMissingElem permMissingElem = new PermMissingElem();

    private int[] array;
    private int result;

    public PermMissingElemTest(int[] array, int result) {
        this.array = array;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{}, 0},
                {new int[]{2}, 1},
                {new int[]{2, 3}, 1},
                {new int[]{1, 3}, 2},
                {new int[]{2, 3, 1, 5}, 4}
        });
    }

    @Test
    public void test() {
        assertThat(permMissingElem.solution(array), is(result));
    }
}