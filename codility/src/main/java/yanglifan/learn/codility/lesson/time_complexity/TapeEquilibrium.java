package yanglifan.learn.codility.lesson.time_complexity;

public class TapeEquilibrium {
    public int run(int[] array) {
        int sum = sum(array);
        int leftSum = 0;
        int diff = 0;
        for (int i = 0; i < array.length - 1; i++) {
            leftSum += array[i];
            int newDiff = Math.abs(leftSum - (sum - leftSum));

            if (i == 0) {
                diff = newDiff;
            } else if (diff > newDiff) {
                diff = newDiff;
            }
        }
        return diff;
    }

    private int sum(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }
}
