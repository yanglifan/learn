package yanglifan.learn.algorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MatrixPathNumTest {
    MatrixPathNum matrixPathNum = new MatrixPathNum();

    int[][] matrix;
    int m;
    int n;
    int r;

    public MatrixPathNumTest(int[][] matrix, int m, int n, int r) {
        this.matrix = matrix;
        this.m = m;
        this.n = n;
        this.r = r;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new int[][]{
                                {1}
                        }, 1, 1, 1
                },
                {
                        new int[][]{
                                {1, 1},
                                {1, 1}
                        }, 2, 2, 2
                },
                {
                        new int[][]{
                                {1, 1, 1},
                                {1, 1, 1},
                                {1, 1, 1}
                        }, 3, 3, 6
                },
                {
                        new int[][]{
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1}
                        }, 3, 3, 2
                },
                {
                        new int[][]{
                                {1, 0, 1},
                                {1, 0, 0},
                                {1, 1, 1}
                        }, 3, 3, 1
                },
                {
                        new int[][]{
                                {1, 0, 1},
                                {1, 1, 0},
                                {1, 1, 1}
                        }, 3, 3, 2
                },
                {
                        new int[][]{
                                {1, 1, 1, 1},
                                {1, 1, 1, 0},
                                {1, 0, 1, 1}
                        }, 3, 4, 3
                },
                {
                        new int[][]{
                                {1, 0},
                                {1, 0},
                                {1, 1}
                        }, 3, 2, 1
                },
                {
                        new int[][]{
                                {1, 1},
                                {1, 0},
                                {1, 1}
                        }, 3, 2, 1
                }
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(matrixPathNum.solution(matrix, m, n), is(r));
    }
}