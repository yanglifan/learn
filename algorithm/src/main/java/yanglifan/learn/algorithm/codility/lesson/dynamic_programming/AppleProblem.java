package yanglifan.learn.algorithm.codility.lesson.dynamic_programming;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AppleProblem {
    public static int solution(int[][] apples) {
        int[][] result = new int[apples.length][apples[0].length];
        for (int i = 0; i < apples.length; i++) {
            for (int j = 0; j < apples[i].length; j++) {
                int maxi = 0, maxj = 0;
                if (i > 0) maxi = result[i - 1][j];
                if (j > 0) maxj = result[i][j - 1];
                result[i][j] = apples[i][j] + (maxi > maxj ? maxi : maxj);
            }
        }
        return result[apples.length - 1][apples[0].length - 1];
    }

    @Test
    public void test() {
        int[][] apples = {{1, 2, 0}, {0, 3, 5}, {0, 6, 1}};
        assertThat(solution(apples), is(13));
    }
}
