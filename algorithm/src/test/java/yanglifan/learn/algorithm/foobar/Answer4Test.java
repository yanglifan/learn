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

        Fraction[][] f = Answer4.toFractionMatrix(m1);
        Fraction[][] matrixR = Answer4.findSubMatrix(f, indices, 0, m1[0].length - indices.length - 1);
        assertThat(matrixR, is(new Fraction[][]{
                new Fraction[]{
                        new Fraction(0, 2),
                        new Fraction(1, 2),
                        new Fraction(0, 2),
                        new Fraction(0, 2)
                },
                new Fraction[]{
                        new Fraction(4, 9),
                        new Fraction(0, 9),
                        new Fraction(0, 9),
                        new Fraction(3, 9)}
        }));
    }

    @Test
    public void finalTest() {
        Answer4.answer(m1);
    }
}