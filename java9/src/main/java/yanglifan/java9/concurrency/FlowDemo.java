package yanglifan.java9.concurrency;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Based on http://www.baeldung.com/java-9-reactive-streams
 *
 * @author Yang Lifan
 */
public class FlowDemo {
    @Test
    public void consumeAll() {
        // given
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        List<String> items = List.of("1", "x", "2", "x", "3", "x");

        // when
        assertThat(publisher.getNumberOfSubscribers(), is(1));
        items.forEach(publisher::submit);
        publisher.close();


    }
}

class EndSubscriber<T> implements Flow.Subscriber<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndSubscriber.class);

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        LOGGER.info(item.toString());
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error", throwable);
    }

    @Override
    public void onComplete() {
        LOGGER.info("Done");
    }
}