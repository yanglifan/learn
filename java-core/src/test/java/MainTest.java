import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
    @Test
    public void assign_from() {
        System.out.println(Interface1.class.isAssignableFrom(Interface2.class));
    }

    @Test
    public void instanceOf() {
        ArrayList arrayList = new ArrayList();
        System.out.println(arrayList instanceof List);
    }
}

interface Interface1 {

}

interface Interface2 extends Interface1 {

}
