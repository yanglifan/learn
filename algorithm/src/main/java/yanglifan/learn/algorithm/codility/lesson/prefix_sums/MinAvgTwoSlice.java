package yanglifan.learn.algorithm.codility.lesson.prefix_sums;

public class MinAvgTwoSlice {
    /**
     * All slices can be consisted of two elem slices and three elem slices. So only calculate two and three elem slices
     */
    public int solution(int[] a) {
        int minAvgIdx = 0;
        double minAvgVal = (a[0] + a[1]) / 2; //At least two elements in A.
        double currAvg;
        for (int i = 0; i < a.length - 2; i++) {
            /**
             * We check first the two-element slice
             */
            currAvg = ((double) (a[i] + a[i + 1])) / 2;
            if (currAvg < minAvgVal) {
                minAvgVal = currAvg;
                minAvgIdx = i;
            }
            /**
             * We check the three-element slice
             */
            currAvg = ((double) (a[i] + a[i + 1] + a[i + 2])) / 3;
            if (currAvg < minAvgVal) {
                minAvgVal = currAvg;
                minAvgIdx = i;
            }
        }

        /**
         * Now we have to check the remaining two elements of the array
         * Inside the for we checked ALL the three-element slices (the last one
         * began at A.length-3) and all but one two-element slice (the missing
         * one begins at A.length-2).
         */
        currAvg = ((double) (a[a.length - 2] + a[a.length - 1])) / 2;
        if (currAvg < minAvgVal) {
            minAvgIdx = a.length - 2;
        }
        return minAvgIdx;
    }
}
