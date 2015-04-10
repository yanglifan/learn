package yanglifan.learn.algorithm.codility.lesson.time_complexity;

public class FrogJmp {
    public int solution(int x, int y, int d) {
        int result = (y - x) / d;
        return (y - x) % d == 0 ? result : result + 1;
    }
}
