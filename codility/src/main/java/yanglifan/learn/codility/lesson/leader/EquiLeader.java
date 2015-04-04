package yanglifan.learn.codility.lesson.leader;

/**
 * A non-empty zero-indexed array A consisting of N integers is given.
 * <p/>
 * The leader of this array is the value that occurs in more than half of the elements of A.
 * <p/>
 * An equi leader is an index S such that 0 ≤ S < N − 1 and two sequences A[0], A[1], ..., A[S] and A[S + 1], A[S + 2], ..., A[N − 1] have leaders of the same value.
 * <p/>
 * For example, given array A such that:
 * <p/>
 * <ul>
 * <li>A[0] = 4
 * <li>A[1] = 3
 * <li>A[2] = 4
 * <li>A[3] = 4
 * <li>A[4] = 4
 * <li>A[5] = 2
 * </ul>
 * we can find two equi leaders:
 * <p/>
 * 0, because sequences: (4) and (3, 4, 4, 4, 2) have the same leader, whose value is 4.
 * 2, because sequences: (4, 3, 4) and (4, 4, 2) have the same leader, whose value is 4.
 * The goal is to count the number of equi leaders.
 * <p/>
 * Write a function:
 * <p/>
 * int solution(int A[], int N);
 * <p/>
 * that, given a non-empty zero-indexed array A consisting of N integers, returns the number of equi leaders.
 * <p/>
 * For example, given:
 * <p/>
 * <ul>
 * <li>A[0] = 4
 * <li>A[1] = 3
 * <li>A[2] = 4
 * <li>A[3] = 4
 * <li>A[4] = 4
 * <li>A[5] = 2
 * </ul>
 * the function should return 2, as explained above.
 * <p/>
 * Assume that:
 * <p/>
 * N is an integer within the range [1..100,000];
 * each element of array A is an integer within the range [−1,000,000,000..1,000,000,000].
 * Complexity:
 * <p/>
 * expected worst-case time complexity is O(N);
 * expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
 * Elements of input arrays can be modified.
 */
public class EquiLeader {
    public static int solution(int[] array) {
        int leader = 0, size = 0;
        for (int i : array) {
            if (size == 0) {
                leader = i;
            }

            if (i == leader) {
                size++;
            } else {
                size--;
            }
        }

        int leaderCount = 0;
        for (int i : array) {
            if (i == leader) leaderCount++;
        }

        if (leaderCount < array.length / 2) return 0;

        int result = 0;
        size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == leader) size++;
            if (size > (i + 1) / 2 && (leaderCount - size) > (array.length - 1 - i) / 2) result++;
        }

        return result;
    }
}
