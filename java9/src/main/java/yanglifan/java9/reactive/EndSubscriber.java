package yanglifan.java9.reactive;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http://www.baeldung.com/java-9-reactive-streams
 *
 * @author Yang Lifan
 */
class EndSubscriber<T> implements Flow.Subscriber<T> {
    List<T> consumedItems = new LinkedList<>();
    private AtomicInteger countOfRemainingItems;
    private Flow.Subscription subscription;

    EndSubscriber(int countOfRemainingItems) {
        this.countOfRemainingItems = new AtomicInteger(countOfRemainingItems);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + item);
        consumedItems.add(item);
        if (countOfRemainingItems.decrementAndGet() > 0) {
            subscription.request(1);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
    }
}
