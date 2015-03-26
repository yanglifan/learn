package yanglifan.learn.core.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class JavaProxyTest {
    @Test
    public void basic_test() {
        List list = (List) Proxy.newProxyInstance(List.class.getClassLoader(), new Class[]{List.class}, new MyDynamicProxyClass(new ArrayList()));
        list.add(new Object());
    }
}
