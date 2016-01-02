package yanglifan.learn.algorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class Decimal2BinaryTest {

    int decimalNumber;
    String binaryNumber;

    public Decimal2BinaryTest(int decimalNumber, String binaryNumber) {
        this.decimalNumber = decimalNumber;
        this.binaryNumber = binaryNumber;
    }

    @Parameterized.Parameters

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, "0"},
                {1, "1"},
                {2, "10"},
                {3, "11"},
                {255, "11111111"}
        });
    }

    @Test
    public void testSolution() throws Exception {
        assertThat(Decimal2Binary.solution(decimalNumber), is(binaryNumber));
    }
}