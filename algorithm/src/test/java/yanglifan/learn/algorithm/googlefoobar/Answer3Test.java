package yanglifan.learn.algorithm.googlefoobar;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Answer3Test {
    @Test
    public void test() {
        assertThat(Answer3.answer(3, new int[]{7, 3, 5, 1}), is(new int[]{-1, 7, 6, 3}));
        assertThat(Answer3.answer(5, new int[]{19, 14, 28}), is(new int[]{21, 15, 29}));
    }

}