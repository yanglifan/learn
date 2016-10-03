package yanglifan.learn.framework;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4JDemo {
    @Test
    public void error_log() throws Exception {
        Logger logger = LoggerFactory.getLogger(Slf4JDemo.class);
        try {
            withException();
        } catch (Exception e) {
            logger.error("Test {}, {}, {}", "hello","world", "hello", e);
        }
    }

    void withException() {
        throw new RuntimeException("with ex");
    }
}
