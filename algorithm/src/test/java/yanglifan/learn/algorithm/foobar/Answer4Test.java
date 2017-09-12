package yanglifan.learn.algorithm.foobar;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Answer4Test {
    private int[][] m1 = new int[][]{
            new int[]{0, 1, 0, 0, 0, 1},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{4, 0, 0, 3, 2, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0}
    };

    private int[][] m2 = new int[][]{
            new int[]{0, 1, 0, 0, 0, 1},
            new int[]{4, 0, 0, 3, 2, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0},
            new int[]{0, 0, 0, 0, 0, 0}
    };

    @Test
    public void testFindSubMatrixIndices() {
        int[] indices = Answer4.findSubMatrixIndices(m1);
        assertThat(indices, is(new Integer[]{0, 3}));
         indices = Answer4.findSubMatrixIndices(m2);
        assertThat(indices, is(new Integer[]{0, 1}));
    }

    @Test
    public void testFindSubMatrix() {
        int[] indices = Answer4.findSubMatrixIndices(m1);
        int[][] subMatrix = Answer4.findSubMatrix(m1, indices, 4, 5);
        assertThat(subMatrix, is(new int[][]{
                new int[]{0, 1},
                new int[]{2, 0}
        }));
    }
}