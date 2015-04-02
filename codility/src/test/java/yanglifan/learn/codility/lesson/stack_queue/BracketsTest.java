package yanglifan.learn.codility.lesson.stack_queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class BracketsTest {

    String s;
    int r;

    public BracketsTest(String s, int r) {
        this.s = s;
        this.r = r;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", 1},
                {"{}", 1},
                {"[]", 1},
                {"()", 1},
                {"[()]", 1},
                {"[()()]", 1},
                {"[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[()()]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]", 1},
                {"[())]", 0},
                {"[", 0},
                {"([)()]", 0},
                {"([", 0},
                {"])", 0}
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(Brackets.solution(s), is(r));
    }
}