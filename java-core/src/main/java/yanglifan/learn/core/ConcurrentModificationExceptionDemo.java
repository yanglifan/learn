package yanglifan.learn.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcurrentModificationExceptionDemo {
    @Test(expected = ConcurrentModificationException.class)
    public void demo() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        for (String value : list) {
            if (value.equals("a")) {
                list.remove(value);
            }
        }
    }

    @Test
    public void demo2() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("a")) {
                list.remove(i);
            }
        }

        System.out.println(list);
    }
}
