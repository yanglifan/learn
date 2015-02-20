package yanglifan.learn.codility.lesson.prefix_sums;

public class PassingCars {
    public int solution(int[] a) {
        int sum = 0;
        for (int i : a) {
            sum += i;
        }

        int aggr = 0;
        int result = 0;
        for (int i : a) {
            if (i == 0) {
                result += (sum - aggr);
                if (result > Integer.MAX_VALUE / 2) return -1;
            } else {
                aggr += i;
            }
        }

        return result;
    }
}
