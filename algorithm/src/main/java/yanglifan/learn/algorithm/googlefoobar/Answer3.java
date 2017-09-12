package yanglifan.learn.algorithm.googlefoobar;

import java.util.HashMap;
import java.util.Map;

/*
Ion Flux Relabeling
===================

Oh no! Commander Lambda's latest experiment to improve the efficiency of her LAMBCHOP doomsday device has backfired spectacularly. She had been improving the structure of the ion flux converter tree, but something went terribly wrong and the flux chains exploded. Some of the ion flux converters survived the explosion intact, but others had their position labels blasted off. She's having her henchmen rebuild the ion flux converter tree by hand, but you think you can do it much more quickly - quickly enough, perhaps, to earn a promotion!

Flux chains require perfect binary trees, so Lambda's design arranged the ion flux converters to form one. To label them, she performed a post-order traversal of the tree of converters and labeled each converter with the order of that converter in the traversal, starting at 1. For example, a tree of 7 converters would look like the following:

   7
 3   6
1 2 4 5

Write a function answer(h, q) - where h is the height of the perfect tree of converters and q is a list of positive integers representing different flux converters - which returns a list of integers p where each element in p is the label of the converter that sits on top of the respective converter in q, or -1 if there is no such converter.  For example, answer(3, [1, 4, 7]) would return the converters above the converters at indexes 1, 4, and 7 in a perfect binary tree of height 3, which is [3, 6, -1].

The domain of the integer h is 1 <= h <= 30, where h = 1 represents a perfect binary tree containing only the root, h = 2 represents a perfect binary tree with the root and two leaf nodes, h = 3 represents a perfect binary tree with the root, two internal nodes and four leaf nodes (like the example above), and so forth.  The lists q and p contain at least one but no more than 10000 distinct integers, all of which will be between 1 and 2^h-1, inclusive.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (int) h = 3
    (int list) q = [7, 3, 5, 1]
Output:
    (int list) [-1, 7, 6, 3]

Inputs:
    (int) h = 5
    (int list) q = [19, 14, 28]
Output:
    (int list) [21, 15, 29]
 */
public class Answer3 {

    public static int[] answer(int h, int[] q) {
        int parent = (int) Math.pow(2, h) - 1;
        int[] result = new int[q.length];
        Map<Integer, Integer> cache = new HashMap<>(q.length);
        for (int i : q) {
            cache.put(i, 0);
        }

        for (int i = 0; i < q.length; i++) {
            if (q[i] < parent) {
                if (cache.get(q[i]) != 0) {
                    result[i] = cache.get(q[i]);
                } else {
                    result[i] = find(parent, q[i], cache);
                }
            } else {
                result[i] = -1;
            }
        }
        return result;
    }

    private static int find(int parent, int target, Map<Integer, Integer> cache) {
        int under = parent / 2;
        int left = parent - 1 - under;
        int right = parent - 1;

        while (left != target && right != target) {
            if (cache.get(left) != null && cache.get(left) == 0) {
                cache.put(left, parent);
            }

            if (cache.get(right) != null && cache.get(right) == 0) {
                cache.put(right, parent);
            }

            if (target <= left) {
                parent = left;
            } else {
                parent = right;
            }

            under /= 2;
            left = parent - 1 - under;
            right = parent - 1;
        }

        return parent;
    }
}
