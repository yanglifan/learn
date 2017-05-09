package yanglifan.learn.algorithm.find_common_ancestor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyFindCommonAncestor implements FindCommonAncestor {
    @Override
    public String findCommonAncestor(String[] commitHashes, String[][] ancestorHashes, String commitHash1, String commitHash2) {
        Map<String, Integer> commitHashesIndex = convert(commitHashes);
        Set<HashHolder> ancestorHashes1 = findAncestorHashes(ancestorHashes, commitHash1, commitHashesIndex);
        Set<HashHolder> ancestorHashes2 = findAncestorHashes(ancestorHashes, commitHash2, commitHashesIndex);

        HashHolder nearestCommonAncestor = null;
        for (HashHolder hashHolder : ancestorHashes1) {
            if (ancestorHashes2.contains(hashHolder)) {
                if (nearestCommonAncestor == null || hashHolder.index < nearestCommonAncestor.index) {
                    nearestCommonAncestor = hashHolder;
                }
            }
        }

        return nearestCommonAncestor == null ? null : nearestCommonAncestor.hash;
    }

    private Set<HashHolder> findAncestorHashes(String[][] ancestorHashes, String commitHash, Map<String, Integer> commitHashesIndex) {
        Set<HashHolder> commitHashAncestors = new HashSet<>(commitHashesIndex.size());
        Stack<String> ancestorStack = new Stack<>();
        String[] nearestAncestors = ancestorHashes[commitHashesIndex.get(commitHash)];

        if (nearestAncestors == null) {
            commitHashAncestors.add(new HashHolder(commitHash, commitHashesIndex.get(commitHash))); // commitHash is the root commit.
            return commitHashAncestors;
        }

        pushAncestorsIfAbsent(ancestorStack, nearestAncestors, commitHashAncestors, commitHashesIndex);
        addAncestors(commitHashAncestors, nearestAncestors, commitHashesIndex);

        while (!ancestorStack.isEmpty()) {
            String ancestor = ancestorStack.pop();
            String[] ancestorsOfAncestor = ancestorHashes[commitHashesIndex.get(ancestor)];

            if (ancestorsOfAncestor == null) {
                continue;
            }

            pushAncestorsIfAbsent(ancestorStack, ancestorsOfAncestor, commitHashAncestors, commitHashesIndex);
            addAncestors(commitHashAncestors, ancestorsOfAncestor, commitHashesIndex);
        }

        return commitHashAncestors;
    }

//    private void addAncestors(Collection<HashHolder> commitHashAncestors, String[] ancestors, Map<String, Integer> commitHashesIndex) {
//        for (String ancestor : ancestors) {
//            commitHashAncestors.add(new HashHolder(ancestor, commitHashesIndex.get(ancestor)));
//        }
//    }

    private void addAncestors(Collection<HashHolder> commitHashAncestors, String[] ancestors, Map<String, Integer> commitHashesIndex) {
        Arrays.stream(ancestors)
                .forEach(ancestor -> commitHashAncestors.add(new HashHolder(ancestor, commitHashesIndex.get(ancestor))));
    }

//    private void pushAncestorsIfAbsent(Stack<String> stack, String[] ancestors, Set<HashHolder> set, Map<String, Integer> commitHashesIndex) {
//
//        for (String ancestor : ancestors) {
//            if (!set.contains(new HashHolder(ancestor, commitHashesIndex.get(ancestor)))) {
//                stack.push(ancestor);
//            }
//        }
//    }

    private void pushAncestorsIfAbsent(Stack<String> stack, String[] ancestors, Set<HashHolder> set, Map<String, Integer> commitHashesIndex) {
        Arrays.stream(ancestors)
                .filter(ancestor -> !set.contains(new HashHolder(ancestor, commitHashesIndex.get(ancestor))))
                .forEach(stack::push);
    }

    private Map<String, Integer> convert(String[] commitHashes) {
        return IntStream.range(0, commitHashes.length).boxed().collect(Collectors.toMap(i -> commitHashes[i], i -> i));
    }

//    private Map<String, Integer> convert(String[] commitHashes) {
//        Map<String, Integer> map = new HashMap<>(commitHashes.length);
//        for (int i = 0; i < commitHashes.length; i++) {
//            map.put(commitHashes[i], i);
//        }
//        return map;
//    }

    private class HashHolder {
        String hash;
        int index;

        public HashHolder(String hash, int index) {
            this.hash = hash;
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HashHolder that = (HashHolder) o;

            return hash.equals(that.hash);

        }

        @Override
        public int hashCode() {
            return hash.hashCode();
        }
    }
}
