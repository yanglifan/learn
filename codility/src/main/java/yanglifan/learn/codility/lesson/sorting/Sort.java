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

        public int[] quickSort() {
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

    public static int[] quickSort(int[] array) {
        return (new QuickSort(array)).quickSort();
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
