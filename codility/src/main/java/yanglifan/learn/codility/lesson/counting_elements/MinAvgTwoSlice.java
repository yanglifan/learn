package yanglifan.learn.codility.lesson.counting_elements;

public class MinAvgTwoSlice {
    public int solution(int[] a) {
        System.out.println(" ");
        for (int i : a) {
            System.out.print(i + ",");
        }
        int index = 0;
        float min = (a[0] + a[1]) / 2;
        for (int i = 0; i < a.length - 1; i++) {
            float current = (a[i] + a[i + 1]) / 2;
            if (current < min) {
                min = current;
                index = i;
            }
        }

        return index;
    }
}
