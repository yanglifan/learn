package yanglifan.learn.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionTraceDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionTraceDemo.class);

    @Test
    public void demo() {
        try {
            method3();
        }catch (Exception e) {
            LOGGER.error("Error", e);
        }
    }

    void method3() {
        try {
            method2();
        } catch (Exception e) {
            throw new RuntimeException("method3", e);
        }
    }

    void method2() {
        try {
            method1();
        } catch (Exception e) {
            throw new RuntimeException("method2", e);
        }
    }

    void method1() throws Exception {
        throw new RuntimeException("method1");
    }
}
