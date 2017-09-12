import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
}
