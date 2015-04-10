package yanglifan.learn.algorithm.codility.lesson.counting_elements;

public class MaxCounters {
    public int[] solution(int n, int[] a) {
        int[] result = new int[n];
        int max = 0;
        int preMax = 0;
        for (int i : a) {
            if (i <= n) {
                result[i - 1] = preMax > result[i - 1] ? preMax + 1 : result[i - 1] + 1;
                if (max < result[i - 1]) {
                    max = result[i - 1];
                }
            } else if (i == n + 1) {
                preMax = max;
            }
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i] < preMax) {
                result[i] = preMax;
            }
        }

        return result;
    }
}
