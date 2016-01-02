package yanglifan.learn.algorithm;

import java.util.Stack;

public class MatrixPathNum {
    public int solution(int[][] matrix, int m, int n) {

        if (m == 1 && n == 1) {
            return matrix[0][0] == 1 ? 1 : 0;
        }

        Stack<int[]> coordinateStack = new Stack<>();
        int pathNumber = 0;

        if (matrix[0][1] == 1) {
            pathNumber++;
            coordinateStack.push(new int[]{0, 1});
        }

        if (matrix[1][0] == 1) {
            pathNumber++;
            coordinateStack.push(new int[]{1, 0});
        }

        while (!coordinateStack.empty()) {
            int[] coordinate = coordinateStack.pop();
            int x = coordinate[0];
            int y = coordinate[1];

            if (y < n - 1 && x < m - 1) {
                if (matrix[x][y + 1] == 1 && matrix[x + 1][y] == 1) {
                    pathNumber++;

                    coordinateStack.push(new int[]{x, y + 1});
                    coordinateStack.push(new int[]{x + 1, y});
                }

                if (matrix[x][y + 1] == 0 && matrix[x + 1][y] == 0 && pathNumber > 0) {
                    pathNumber--;
                }
            }

            if (y < n - 1 && x == m - 1 && matrix[x][y + 1] == 0 && pathNumber > 0) {
                if (matrix[x][y + 1] == 0 && pathNumber > 0) {
                    pathNumber--;
                }

                if (matrix[x][y + 1] == 1) {
                    coordinateStack.push(new int[]{x, y + 1});
                }
            }

            if (y == n - 1 && x < m - 1 && matrix[x + 1][y] == 0 && pathNumber > 0) {
                if (matrix[x + 1][y] == 0 && pathNumber > 0) {
                    pathNumber--;
                }

                if (matrix[x + 1][y] == 1) {
                    coordinateStack.push(new int[]{x + 1, y});
                }
            }
        }

        return pathNumber;
    }
}
