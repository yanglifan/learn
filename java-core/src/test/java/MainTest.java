import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

interface Interface1 {

}

interface Interface2 extends Interface1 {

}

public class MainTest {
    @Test
    public void assign_from() {
        System.out.println(Interface1.class.isAssignableFrom(Interface2.class));
    }

    @Test
    public void instanceOf() {
        ArrayList arrayList = new ArrayList();
        System.out.println(arrayList instanceof List);
        //language=JSON
        String json = "{\"name\":\"yanglifan\"}";
    }

    @Test
    public void test() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            throw null;
        }
    }

    @Test
    public void map_eq() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("A", "A");
        map1.put("B", "B");
        Map<String, String> map2 = new HashMap<>();
        map2.put("A", "A");
        map2.put("B", "B");
        assertEquals(map1, map2);
    }
}
