package yanglifan.learn.algorithm.codility.lesson.dynamic_programming;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BagProblem {
    public static int findMaxValue(int[] capacities, int[] values, int bagCapacity) {
        int[][] result = solve(capacities, values, bagCapacity);
        return result[capacities.length][bagCapacity];
    }

    private static int[][] solve(int[] capacities, int[] values, int bagCapacity) {
        assert capacities.length == values.length;

        int[][] result = new int[capacities.length + 1][bagCapacity + 1];

        for (int i = 1; i <= capacities.length; i++) {
            for (int j = 1; j <= bagCapacity; j++) {
                result[i][j] = result[i - 1][j];
                System.out.println("i: " + i + " j: " + j + " v: " + result[i][j]);
                if (j >= capacities[i - 1]) {
                    int sub = result[i - 1][j - capacities[i - 1]] + values[i - 1];
                    System.out.println("sub: " + sub);
                    result[i][j] = result[i][j] > sub ? result[i][j] : sub;
                }
            }
        }

        return result;
    }

    public static int[] findItems(int[] capacities, int[] values, int bagCapacity) {
        int[][] matrix = solve(capacities, values, bagCapacity);
        int[] result = new int[capacities.length];
        int j = bagCapacity;
        for (int i = capacities.length; i > 0; i--) {
            if (matrix[i][j] > matrix[i - 1][j]) {
                result[i - 1] = 1;
                j = j - capacities[i - 1];
            }
        }
        return result;
    }

    @Test
    public void test1() {
        int[] c1 = {1, 2, 3};
        int[] v1 = {1, 2, 3};
        assertThat(findMaxValue(c1, v1, 5), is(5));
        assertThat(findMaxValue(c1, v1, 4), is(4));
        assertThat(findMaxValue(c1, v1, 3), is(3));
    }

    @Test
    public void test2() {
        int[] c2 = {4, 3, 5, 2, 5};
        int[] v2 = {9, 6, 1, 4, 1};
        assertThat(findMaxValue(c2, v2, 10), is(19));
        assertThat(findMaxValue(c2, v2, 2), is(4));
    }

    @Test
    public void test_find_items_1() {
        int[] c2 = {4, 3, 5, 2, 5};
        int[] v2 = {9, 6, 1, 4, 1};
        assertThat(findItems(c2, v2, 10), is(new int[]{1, 1, 0, 1, 0}));
    }

    @Test
    public void test_find_items_2() {
        int[] c2 = {5, 4, 3, 5, 2};
        int[] v2 = {1, 9, 6, 1, 4};
        assertThat(findItems(c2, v2, 10), is(new int[]{0, 1, 1, 0, 1}));
    }
}
