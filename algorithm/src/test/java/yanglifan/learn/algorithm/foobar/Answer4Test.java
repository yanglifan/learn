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


    @Test
    public void test1() {
        assertThat(Answer4.answer(m1), is(new int[]{0, 3, 2, 9, 14}));
    }

    @Test
    public void test2() {
        assertThat(Answer4.answer(m3), is(new int[]{7, 6, 8, 21}));
    }

    @Test
    public void test() {
        assertThat(Answer4.answer(new int[][]{
                {0, 1, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {0, 0, 3, 1, 0}
        }), is(new int[]{1, 1}));
    }

    @Test
    public void test3() {
        assertThat(Answer4.answer(new int[][]{
                {1, 2, 3, 0, 0, 0},
                {4, 5, 6, 0, 0, 0},
                {7, 8, 9, 1, 0, 0},
                {0, 0, 0, 0, 1, 2},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        }), is(new int[]{1, 2, 3}));
    }

    @Test
    public void test4() {
        assertThat(Answer4.answer(new int[][]{
                {0}
        }), is(new int[]{1, 1}));
    }

    @Test
    public void test5() {
        assertThat(Answer4.answer(new int[][]{
                {0, 1},
                {0, 0}
        }), is(new int[]{1, 1}));
    }

    @Test
    public void test6() {
        assertThat(Answer4.answer(new int[][]{
                {0, 86, 61, 189, 0, 18, 12, 33, 66, 39},
                {0, 0, 2, 0, 0, 1, 0, 0, 0, 0},
                {15, 187, 0, 0, 18, 23, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        }), is(new int[]{6, 44, 4, 11, 22, 13, 100}));
    }
}