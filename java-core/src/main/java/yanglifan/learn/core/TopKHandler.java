package yanglifan.learn.core;

import java.util.*;

public class TopKHandler {
    Set<Integer> indexRecord = new HashSet<>();
    Set<Integer> heapRecord = new HashSet<>();
    int[] source;

    public TopKHandler(int[] source) {
        this.source = source;
    }

    /**
     * This is the main method to implement the top k function. Use heap sort to find the top k.
     */
    public Integer[] solve(int k) {
        Comparator<Integer> descComparator = (t1, t2) -> t2 - t1;
        PriorityQueue<Integer> heap = new PriorityQueue<>(descComparator);

        generateRandomHeap(source, k, heap);
        Integer[] topK = getInitTopK(heap, k);
        Integer bound = topK[k - 1];

        for (int i = 0; i < source.length; i++) {
            if (reachGroupSize(k, i) || reachTotalSize(source, i)) {
                addIntoHeap(heap, i, bound);
                addPreTopKIntoHeap(heap, topK);
                getTopK(k, heap, topK);
                bound = topK[k - 1];
                heap = new PriorityQueue<>(descComparator);
            } else {
                addIntoHeap(heap, i, bound);
            }
        }

        return topK;
    }

    private void getTopK(int k, PriorityQueue<Integer> heap, Integer[] topK) {
        for (int j = 0; j < k; j++) {
            Integer heapElement = heap.poll();
            if (heapElement == null) continue;
            topK[j] = heapElement;
        }
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

    private void addIntoHeap(PriorityQueue<Integer> heap, int index, Integer bound) {
        if (indexRecord.contains(index)) return;

        if (heapRecord.contains(source[index])) return;

        if (bound == null || source[index] > bound) {
            heap.add(source[index]);
            heapRecord.add(source[index]);
        }
    }
}
