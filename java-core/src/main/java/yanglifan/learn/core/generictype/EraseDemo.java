package yanglifan.learn.core.generictype;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class EraseDemo {
    @Test
    public void is_same_class() {
        Class c1 = new ArrayList<Integer>().getClass();
        Class c2 = new ArrayList<String>().getClass();
        assertTrue(c1 == c2);
    }

    @Test
    public void getTypedParameters() {
        List<Integer> list = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
    }
}
