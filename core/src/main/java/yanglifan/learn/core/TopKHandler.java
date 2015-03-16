package yanglifan.learn.core;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TopKHandler {
    private final static int TOTAL = 10000000;

    Set<Integer> indexRecord = new HashSet<>();
    int[] source;

    public TopKHandler() {
    }

    public TopKHandler(int[] source) {
        this.source = source;
    }

    private int[] randomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            int a = random.nextInt();
            array[i] = a;
        }

        return array;
    }

    private int[] orderArray(int size) {
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        return array;
    }

//    @Test
    public void test_random() {
        System.out.println("========== main_test ==========");
        int[] source = randomArray(10000000);
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.getTopK(100);
        for (int i : topK) {
            System.out.println(i);
        }
    }

//    @Test
    public void test_order_array() {
        System.out.println("========== test_order_array ==========");
        int[] source = orderArray(10_000_000);
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.getTopK(100);
        StringBuilder builder = new StringBuilder();
        for (int i : topK) {
            builder.append(i);
            if (i != 9_999_999) {
                builder.append(", ");
            }
        }
        System.out.println("Test Order Array result is " + builder.toString());
    }

    @Test
    public void simple_test_1() {
        System.out.println("========== simple_test_1 ==========");
        int[] source = new int[]{1, 2, 3, 4, 5, 6};
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.getTopK(2);
        assertThat(topK, is(new Integer[]{6, 5}));
    }

    @Test
    public void simple_test_2() {
        System.out.println("========== simple_test_2 ==========");
        int[] source = new int[]{0, 0, 0, 0, 0};
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.getTopK(2);
        assertThat(topK, is(new Integer[]{0, 0}));
    }

    @Test
    public void simple_test_3() {
        System.out.println("========== simple_test_3 ==========");
        int[] source = new int[]{1, 5, 18, -4, 99, 3, 0, 67, 1, 2, 11};
        TopKHandler topKHandlerHandler = new TopKHandler(source);
        Integer[] topK = topKHandlerHandler.getTopK(3);
        assertThat(topK, is(new Integer[]{99, 67, 18}));
    }

    /**
     * This is the main method to implement the top k function.
     *
     * @param k
     * @return
     */
    public Integer[] getTopK(int k) {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return t1 - integer;
            }
        };

        PriorityQueue<Integer> heap = new PriorityQueue<>(comparator);

        generateRandomHeap(source, k, heap);
        Integer[] topK = getInitTopK(heap, k);
        Integer bound = topK[k - 1];

        for (int i = 0; i < source.length; i++) {
            if (reachGroupSize(k, i) || reachTotalSize(source, i)) {
                addIntoHeap(heap, source[i], bound);
                addPreTopKIntoHeap(heap, topK);

                for (int j = 0; j < k; j++) {
                    Integer heapElement = heap.poll();

                    if (heapElement == null) continue;

                    topK[j] = heapElement;

                    System.out.println(topK[j]);
                    if (j == k - 1) {
                        System.out.println("Update the down bound as " + topK[j]);
                        bound = topK[j];
                    }
                }
                heap = new PriorityQueue<>(comparator);
            } else {
                addIntoHeap(heap, source[i], bound);
            }
        }

        return topK;
    }

    private Integer[] getInitTopK(PriorityQueue<Integer> heap, int k) {
        Integer[] topK = new Integer[k];
        for (int i = 0; i < k; i++) {
            topK[i] = heap.poll();
        }
        return topK;
    }

    private void generateRandomHeap(int[] source, int k, PriorityQueue<Integer> heap) {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(source.length);
            while (indexRecord.contains(index)) {
                index = random.nextInt(source.length);
            }
            indexRecord.add(index);
            heap.add(source[index]);
        }
    }

    private void addPreTopKIntoHeap(PriorityQueue<Integer> heap, Integer[] topK) {
        for (Integer a : topK) {
            if (a != null)
                heap.add(a);
        }
    }

    private boolean reachTotalSize(int[] source, int i) {
        return i == source.length - 1;
    }

    private boolean reachGroupSize(int groupSize, int i) {
        return i % groupSize == groupSize - 1;
    }

    private void addIntoHeap(PriorityQueue<Integer> heap, int value, Integer bound) {
        if (bound == null || value > bound) {
            System.out.println("Add " + value + " into the heap");
            heap.add(value);
        }
    }
}
