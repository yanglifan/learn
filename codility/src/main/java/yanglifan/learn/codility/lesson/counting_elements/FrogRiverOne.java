package yanglifan.learn.codility.lesson.counting_elements;

public class FrogRiverOne {
    public int solution(int x, int[] a) {
        boolean[] flags = new boolean[x];
        int factorial = factorial(x);
        int realSum = 0;
        for (int i = 0; i < a.length; i++) {
            if (!flags[a[i] - 1]) {
                flags[a[i] - 1] = true;
                realSum += a[i];
            }

            if (realSum == factorial) {
                return i;
            }
        }
        return -1;
    }

    private int factorial(int x) {
        int sum = 0;
        for (int i = 0; i < x; i++) {
            sum += (i + 1);
        }
        return sum;
    }
}
