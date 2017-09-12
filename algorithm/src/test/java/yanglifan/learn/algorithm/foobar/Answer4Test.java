package yanglifan.learn.algorithm.foobar;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Answer4Test {
    private int[][] m1 = new int[][]{
            new int[]{0, 1, 0, 0, 0, 1},
            new int[]{4, 0, 0, 3, 2, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0}
    };

    private int[][] m2 = {
            {0, 1, 0, 0, 0, 1},
            {4, 0, 0, 3, 2, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
    };

    private int[][] m3 = {{0, 2, 1, 0, 0}, {0, 0, 0, 3, 4}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
    private int[][] m4 = {{0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    @Test
    public void testFindSubMatrixIndices() {
        int[] indices = Answer4.findSubMatrixIndices(m1);
        assertThat(indices, is(new Integer[]{0, 1}));
        indices = Answer4.findSubMatrixIndices(m2);
        assertThat(indices, is(new Integer[]{0, 1}));
    }

    @Test
    public void finalTest1() {
        assertThat(Answer4.answer(m1), is(new int[]{0, 3, 2, 9, 14}));
    }

    @Test
    public void finalTest2() {
        assertThat(Answer4.answer(m3), is(new int[]{7, 6, 8, 21}));
    }

    @Test
    public void test3() {
        Answer4.answer(m4);
    }
}