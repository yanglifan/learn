package yanglifan.learn.algorithm.codility.lesson.time_complexity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TapeEquilibriumTest {
    private TapeEquilibrium tapeEquilibrium = new TapeEquilibrium();
    private int[] input;
    private int result;

    public TapeEquilibriumTest(List<Integer> input, int result) {
        this.input = new int[input.size()];
        for (int i = 0; i < input.size(); i++) {
            this.input[i] = input.get(i);
        }
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Arrays.<Integer>asList(0, 0), 0},
                {Arrays.<Integer>asList(0, 0, 0), 0},
                {Arrays.<Integer>asList(1, 0, 1), 0},
                {Arrays.<Integer>asList(1, 0, 0), 1},
                {Arrays.<Integer>asList(-1000, 1000), 2000},
                {Arrays.<Integer>asList(3, 1, 2, 4, 3), 1}
        });
    }

    @Test
    public void test() {
        assertEquals(result, tapeEquilibrium.run(input));
    }
}