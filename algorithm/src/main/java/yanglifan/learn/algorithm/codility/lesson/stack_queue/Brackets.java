package yanglifan.learn.algorithm.codility.lesson.stack_queue;

import java.util.Stack;

public class Brackets {
    public static int solution(String s) {
        if (s.length() == 0) return 1;
        if (s.length() % 2 != 0) return 0;

        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }

            if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) return 0;
                char pair = stack.pop();
                boolean match = isMatch(c, pair);
                if (!match) {
                    return 0;
                }
            }
        }

        return stack.isEmpty() ? 1 : 0;
    }

    private static boolean isMatch(char c, char pair) {
        return (c == ')' && pair == '(') || (c == '}' && pair == '{') || (c == ']' && pair == '[');
    }
}
