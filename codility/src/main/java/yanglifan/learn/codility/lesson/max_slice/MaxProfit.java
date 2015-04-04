package yanglifan.learn.codility.lesson.max_slice;

public class MaxProfit {
    public static int solution(int[] a) {
        if (a.length == 0) return 0;

        for (int i = a.length - 1; i > 0; i--) {
            a[i] = a[i] - a[i - 1];
        }
        a[0] = 0;

        int maxSlice = 0, maxProfit = 0;
        for (int i : a) {
            maxSlice += i;
            maxSlice = maxSlice > 0 ? maxSlice : 0;
            maxProfit = maxProfit > maxSlice ? maxProfit : maxSlice;
        }

        return maxProfit;
    }
}
