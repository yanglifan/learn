package yanglifan.learn.algorithm.codility.lesson.prefix_sums;

public class GenomicRangeQuery {
    public static int[] solution(String s, int[] p, int[] q) {
        int[][] countArray = new int[4][s.length() + 1];

        int idx;
        char[] sequence = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            switch (sequence[i]) {
                case 'A':
                    idx = 0;
                    break;
                case 'C':
                    idx = 1;
                    break;
                case 'G':
                    idx = 2;
                    break;
                default:
                    idx = 3;
            }

            for (int j = 0; j < 4; j++) {
                countArray[j][i + 1] = countArray[j][i];
            }

            countArray[idx][i + 1]++;
        }

        int[] result = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (countArray[j][q[i] + 1] - countArray[j][p[i]] > 0) {
                    result[i] = j + 1;
                    break;
                }
            }
        }

        return result;
    }
}
