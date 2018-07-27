package yanglifan.learn.core.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Lifan
 */
public class ArraysDemo {
    @Test(expected = UnsupportedOperationException.class)
    public void asList() {
        List<String> list = Arrays.asList("a", "b");
        list.add("c");
    }
}
