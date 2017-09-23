package yanglifan.java9.reactive;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

public class EndSubscriberTest {
    @Test
    public void consumeAll() throws Exception {
        // Given
        List<String> items = List.of("1", "x", "2", "x", "3", "x");
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>(items.size());
        publisher.subscribe(subscriber);

        // When
        assertThat(publisher.getNumberOfSubscribers()).isEqualTo(1);
        items.forEach(publisher::submit);
        System.out.println("[" + Thread.currentThread().getName() + "] Publish All");
        publisher.close();

        // Then
        await().atMost(5, TimeUnit.SECONDS).until(() ->
                assertThat(subscriber.consumedItems).containsExactlyElementsOf(items)
        );
    }
}