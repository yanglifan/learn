package yanglifan.learn.algorithm.codility.lesson.prefix_sums;

public class CountDiv {
    public int solution(int a, int b, int k) {
        int add = a % k == 0 ? 1 : 0;
        return (b / k) - (a / k) + add;
    }
}
