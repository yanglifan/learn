package yanglifan.learn.algorithm.codility;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Dijkstra {
    public static int[] solution(int[][] matrix, int startNode) {
        int[] distances = new int[matrix[startNode].length];
        int[] preNodes = new int[matrix[startNode].length];
        boolean[] visited = new boolean[matrix[startNode].length];

        // Init distances array
        for (int i = 0; i < matrix[startNode].length; i++) {
            distances[i] = matrix[startNode][i];
        }

        // Init self
        visited[startNode] = true;
        distances[startNode] = 0;

        for (int i = 1; i < matrix[startNode].length; i++) {
            int min = Integer.MAX_VALUE;
            int k = 0;
            for (int j = 0; j < matrix[startNode].length; j++) {
                if (!visited[j] && distances[j] < min) {
                    min = distances[j];
                    k = j;
                }
            }

            visited[k] = true;
            if (preNodes[k] == 0)
                preNodes[k] = startNode;

            for (int j = 0; j < distances.length; j++) {
                int tmp = matrix[k][j] == Integer.MAX_VALUE ? Integer.MAX_VALUE : (min + matrix[k][j]);
                if (!visited[j] && tmp < distances[j]) {
                    distances[j] = tmp;
                    preNodes[j] = k;
                }
            }
        }

        return distances;
    }

    @Test
    public void test() {
        int[][] matrix1 = new int[][]{
                {0, 5, 10, Integer.MAX_VALUE},
                {5, 0, Integer.MAX_VALUE, 2},
                {10, Integer.MAX_VALUE, 0, 1},
                {Integer.MAX_VALUE, 2, 1, 0}
        };

        assertThat(solution(matrix1, 0), is(new int[]{0, 5, 8, 7}));
        assertThat(solution(matrix1, 1), is(new int[]{5, 0, 3, 2}));
    }
}