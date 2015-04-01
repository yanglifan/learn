package yanglifan.learn.codility.lesson.sorting;

import java.util.Stack;

public class Sort {
    private static boolean debug = true;

    private static class QuickSort {
        private Stack<int[]> rangeStack;
        private int[] array;

        public QuickSort(int[] array) {
            this.array = array;
            rangeStack = new Stack<>();
            rangeStack.push(new int[]{0, array.length - 1});
        }

        public int[] sort() {
            int[] position = rangeStack.pop();

            while (position != null) {
                int start = position[0];
                int end = position[1];

                print(array, start, end);

                int origStart = start;
                int origEnd = end;

                int pivot = array[start];

                while (start < end) {
                    while (start < end && array[end] > pivot) {
                        end--;
                        print(array, start, end);
                    } // miss "start < end"

                    array[start] = array[end];

                    print(array, start, end);

                    while (start < end && array[start] < pivot) {
                        start++;
                        print(array, start, end);
                    }

                    array[end] = array[start];

                    print(array, start, end);
                }

                array[start] = pivot;
                print(array, start, end);

                if (origStart < start - 1)
                    rangeStack.push(new int[]{origStart, start - 1});
                if (end + 1 < origEnd)
                    rangeStack.push(new int[]{end + 1, origEnd});

                if (rangeStack.isEmpty())
                    position = null;
                else
                    position = rangeStack.pop();
            }

            return array;
        }
    }

    private static class HeapSort {
        int[] array;

        public HeapSort(int[] array) {
            this.array = array;
        }

        public int[] sort() {
            heapify(array.length - 1);
            int count = array.length - 1;
            int tmp;

            while (count > 0) {
                tmp = array[0];
                array[0] = array[count];
                array[count] = tmp;
                heapify(--count);
            }
            return array;
        }

        private void heapify(int count) {
            for (int i = (count - 1) / 2; i >= 0; i--) {
                shiftDown(i, count);
            }
        }

        private void shiftDown(int start, int end) {
            int parentIdx = start;
            for (int leftIdx = parentIdx * 2 + 1; leftIdx <= end; parentIdx = leftIdx, leftIdx = leftIdx * 2 + 1) {
                int largeIdx = leftIdx < end && array[leftIdx] < array[leftIdx + 1] ? leftIdx + 1 : leftIdx;
                if (array[parentIdx] < array[largeIdx]) {
                    int tmp = array[parentIdx];
                    array[parentIdx] = array[largeIdx];
                    array[largeIdx] = tmp;
                }
            }
        }
    }

    public static int[] quickSort(int[] array) {
        return (new QuickSort(array)).sort();
    }

    public static int[] heapSort(int[] array) {
        return (new HeapSort(array)).sort();
    }

    private static void print(int[] array, int start, int end) {
        if (!debug || array.length > 100) return;

        System.out.println();
        System.out.print("array ::> [");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i != array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
        System.out.print("; start ::> " + start);
        System.out.print("; end ::> " + end);
    }
}
