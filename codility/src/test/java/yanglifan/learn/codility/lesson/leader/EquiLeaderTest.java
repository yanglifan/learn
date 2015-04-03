package yanglifan.learn.codility.lesson.leader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class EquiLeaderTest {

    int[] a;
    int r;

    public EquiLeaderTest(int[] a, int r) {
        this.a = a;
        this.r = r;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{4, 3, 4, 4, 4, 2}, 2},
                {new int[]{0}, 0},
                {new int[]{0, 0}, 1},
                {new int[]{0, 0, 0}, 2},
                {new int[]{0, 0, 1}, 0},
                {new int[]{0, 1}, 0}
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(EquiLeader.solution(a), is(r));
    }
}