package yanglifan.learn.codility.lesson.leader;

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
