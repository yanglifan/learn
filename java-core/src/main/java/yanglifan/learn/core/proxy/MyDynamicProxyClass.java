package yanglifan.learn.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MyDynamicProxyClass implements InvocationHandler {
    private List list;


    public MyDynamicProxyClass(List list) {
        this.list = list;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("add")) {
            System.out.printf("The method [%s] starts with add.", method.getName());
        }

        return method.invoke(list, args);
    }
}
