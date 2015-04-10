package yanglifan.learn.codility.lesson.binary_search;

public class MinMaxDivision {
    public static int solution(int k, int m, int[] a) {
        // Init max sum bounds
        int minSum = 0, maxSum = 0;
        for (int i : a) {
            if (i > minSum) minSum = i;
            maxSum += i;
        }

        // While loop compare min bound and max bound
        // In loop, divide according to the middle value
        // Update bound and middle value
        while (minSum <= maxSum) {
            int middleSum = (minSum + maxSum) / 2;
//            System.out.printf("%d, %d, %d \n", minSum, maxSum, middleSum);
            if (divide(middleSum, a) > k) {
                minSum = middleSum + 1;
            } else {
                maxSum = middleSum - 1;
            }
        }

        return minSum;
    }

    /**
     * Divide an array, make every part's sum less than the target value.
     *
     * @return The number of the part
     */
    private static int divide(int target, int[] array) {
        int sum = 0, partNum = 1;
        for (int i : array) {
            if (sum + i > target) {
                partNum++;
                sum = i;
            } else {
                sum += i;
            }
        }
//        System.out.println("Part num: " + partNum);
        return partNum;
    }
}
