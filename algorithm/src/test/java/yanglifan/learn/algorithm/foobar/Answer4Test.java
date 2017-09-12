package yanglifan.learn.algorithm.foobar;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Answer4Test {
    @Test
    public void testFindSubMatrixIndices() {
        int[] indices = Answer4.findSubMatrixIndices(new int[][]{
                new int[]{0, 1, 0, 0, 0, 1},
                new int[]{0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0},
                new int[]{4, 0, 0, 3, 2, 0},
                new int[]{0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0}
        });

        assertThat(indices, is(new Integer[]{0, 3}));
    }
}