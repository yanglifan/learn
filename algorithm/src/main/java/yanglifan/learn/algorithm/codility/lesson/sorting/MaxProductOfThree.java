package yanglifan.learn.algorithm.codility.lesson.sorting;

/**
 * Lesson 4 - MaxProductOfThree
 * Pass 100% but code is ugly and finish too slow
 */
public class MaxProductOfThree {
    public static int solution(int[] a) {
        heapify(a, a.length - 1, true);
        int size = a.length - 1;
        int posResult = 1;
        int count = 0;

        int[] positive = new int[3];

        while (size >= 0 && count < 3) {
            int tmp = a[0];
            a[0] = a[size];
            a[size] = tmp;
            positive[count++] = tmp;
            heapify(a, --size, true);
        }

        for (int i : positive) {
            posResult *= i;
        }

        int[] negative = new int[2];
        heapify(a, a.length - 1, false);
        size = a.length - 1;
        count = 0;
        while (size >= 0 && count < 2) {
            int tmp = a[0];
            a[0] = a[size];
            a[size] = tmp;

            if (tmp < 0)
                negative[count++] = tmp;
            heapify(a, --size, false);
        }

        int negResult = 1;
        for (int i : negative) {
            if (i != 0)
                negResult *= i;
        }
        negResult *= positive[0];

        return posResult > negResult ? posResult : negResult;
    }

    private static void heapify(int[] array, int count, boolean greater) {
        for (int i = (count - 1) / 2; i >= 0; i--) {
            shiftDown(array, i, count, greater);
        }
    }

    private static void shiftDown(int[] array, int start, int end, boolean greater) {
        int parentIdx = start;
        for (int leftIdx = parentIdx * 2 + 1; leftIdx <= end; parentIdx = leftIdx, leftIdx = leftIdx * 2 + 1) {
            int largeIdx = leftIdx < end && compare(array[leftIdx + 1], array[leftIdx], greater) ? leftIdx + 1 : leftIdx;
            if (compare(array[largeIdx], array[parentIdx], greater)) {
                int tmp = array[parentIdx];
                array[parentIdx] = array[largeIdx];
                array[largeIdx] = tmp;
            }
        }
    }

    private static boolean compare(int a, int b, boolean greater) {
        if (greater)
            return a > b;
        else
            return b > a;
    }
}
