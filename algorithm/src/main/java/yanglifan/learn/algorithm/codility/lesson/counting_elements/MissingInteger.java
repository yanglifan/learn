package yanglifan.learn.algorithm.codility.lesson.counting_elements;

public class MissingInteger {
    public int solution(int[] a) {
        int[] b = new int[a.length];
        for (int i : a) {
            if (i > 0 && i <= a.length) {
                b[i - 1] = i;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (b[i] == 0) {
                return i + 1;
            }
        }
        return b.length + 1;
    }
}
