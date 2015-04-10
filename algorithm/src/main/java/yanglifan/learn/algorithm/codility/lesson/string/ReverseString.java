package yanglifan.learn.algorithm.codility.lesson.string;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReverseString {
    public static String solution(String source) {
        char[] chars = source.toCharArray();
        char tmp;
        for (int i = 0; i < chars.length / 2; i++) {
            tmp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - i - 1] = tmp;
        }

        return String.valueOf(chars);
    }

    @Test
    public void test() {
        assertThat(solution("abcd"), is("dcba"));
        assertThat(solution("abcdf"), is("fdcba"));
        assertThat(solution("a"), is("a"));
        assertThat(solution(""), is(""));
    }
}
