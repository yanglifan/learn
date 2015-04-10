package yanglifan.learn.codility.lesson.time_complexity;

/**
 * I think some of test cases in codility.com are wrong
 */
public class PermMissingElem {
    public int solution(int[] array) {
        if (array.length == 0) return 0;
        if (array.length == 1) return 1;

        int expectedSum = 0;
        int realSum = 0;
        for (int i = 0; i < array.length; i++) {
            expectedSum += (i + 1);
            realSum += array[i];
        }
        return array.length + 1 - (realSum - expectedSum);
    }
}
