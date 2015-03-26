package yanglifan.learn.concurrency.dsl;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionLogger implements Consumer<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionLogger.class);

    @Override
    public void accept(Throwable throwable) {
        LOGGER.error(throwable.getMessage(), throwable);
    }
}
