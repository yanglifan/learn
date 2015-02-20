package yanglifan.learn.codility.lesson.counting_elements;

public class PermCheck {
    public int solution(int[] array) {
        int[] flagArray = new int[array.length];
        for (int value : array) {
            if (value > array.length) return 0;
            if (flagArray[value - 1] == 1) return 0;
            flagArray[value - 1] = 1;
        }
        return 1;
    }
}
