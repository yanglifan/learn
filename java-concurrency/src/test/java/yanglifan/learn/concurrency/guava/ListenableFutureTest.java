package yanglifan.learn.concurrency.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.*;
import org.junit.Test;

public class ListenableFutureTest {
    @Test
    public void create_a_listenable_future() throws Exception {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<Explosion> explosion = service.submit(new Callable<Explosion>() {
            @Override
            public Explosion call() throws Exception {
                return pushBigRedButton();
            }
        });

        Futures.addCallback(explosion, new FutureCallback<Explosion>() {
            @Override
            public void onSuccess(Explosion explosion) {
                walkAwayFrom(explosion);
            }

            @Override
            public void onFailure(Throwable throwable) {
                battleArchNemesis(); // escaped the explosion!
            }


        });
    }

    private void walkAwayFrom(Explosion explosion) {

    }

    private void battleArchNemesis() {
    }

    private Explosion pushBigRedButton() {
        return new Explosion();
    }
}
