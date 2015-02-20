package yanglifan.learn.concurrency.dsl;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConcurrentDslBuilderTest {
    private StringCounterSaver stringCounterSaver;
    private StringIndexerSaver stringIndexerSaver;

    @Before
    public void setUp() throws Exception {
        stringCounterSaver = new StringCounterSaver();
        stringIndexerSaver = new StringIndexerSaver();
    }

    @Test
    public void one_two_two_sync_chain() throws Exception {
        ConcurrentDslBuilder
                .startCall(Mode.SYNC, () -> "start")
                .thenCall(new StringCounter(), new StringIndexer("ta"))
                .thenCall(stringCounterSaver, stringIndexerSaver);

        assertThat(stringCounterSaver.getCount(), is(5));
        assertThat(stringIndexerSaver.getPosition(), is(1));
    }

    @Test
    public void one_two_two_async_chain() throws Exception {
        ConcurrentDslBuilder
                .startCall(() -> "start")
                .thenCall(new StringCounter(), new StringIndexer("ta"))
                .thenCall(stringCounterSaver, stringIndexerSaver)
                .run();

        TimeUnit.SECONDS.sleep(5);

        assertThat(stringCounterSaver.getCount(), is(5));
        assertThat(stringIndexerSaver.getPosition(), is(1));
    }

    class StringCounter implements Function<String, Integer> {
        @Override
        public Integer apply(String s) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.length();
        }
    }

    class StringIndexer implements Function<String, Integer> {
        private String target;

        StringIndexer(String target) {
            this.target = target;
        }

        @Override
        public Integer apply(String value) {
            return value.indexOf(target);
        }
    }

    class StringCounterSaver implements Function<Map<Class, Object>, Void> {
        private int count;

        @Override
        public Void apply(Map<Class, Object> map) {
            count = (Integer) map.get(StringCounter.class);
            System.out.println("StringCounter: " + count);
            return null;
        }

        public int getCount() {
            return count;
        }
    }

    class StringIndexerSaver implements Function<Map<Class, Object>, Void> {
        private int position;

        @Override
        public Void apply(Map<Class, Object> map) {
            position = (Integer) map.get(StringIndexer.class);
            System.out.println("StringIndexer: " + position);
            return null;
        }

        public int getPosition() {
            return position;
        }
    }
}
