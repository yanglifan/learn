package yanglifan.learn.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RemoveDuplication {
    /**
     * Time and space complexity is o(n)
     */
    @Test
    public void test() {
        Integer[] array = {1, 5, 4, 2, 7, 2, 6, 5};
        Set<Integer> set = new LinkedHashSet<>(Arrays.asList(array));
        assertThat(set.toArray(), is(new Integer[]{1, 5, 4, 2, 7, 6}));
    }
}
