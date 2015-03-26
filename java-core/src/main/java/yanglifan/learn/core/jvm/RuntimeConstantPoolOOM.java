package yanglifan.learn.core.jvm;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Understanding the JVM 2nd Edition. Code List 2-7
 */
public class RuntimeConstantPoolOOM {

    @Test
    public void test_string_intern() {
        // In JDK7, intern will return the reference of the first appear string.
        String str1 = new StringBuilder("Computer").append("Software").toString();
        assertTrue(str1.intern() == str1);

        // "java" is a keyword, so it was appear before.
        String str2 = new StringBuilder("ja").append("va").toString();
        assertFalse(str2.intern() == str2);
    }
}
