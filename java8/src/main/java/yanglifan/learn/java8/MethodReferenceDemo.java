package yanglifan.learn.java8;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MethodReferenceDemo {

    @Test
    public void demo() {
        Integer example = 1;

        Supplier<String> stringSupplier = example::toString;
        assertThat(stringSupplier.get(), is(example.toString()));


        // We can bind to static method, here the generic parameter are the method parameter and return type
        Supplier<Long> s1 = System::currentTimeMillis;

        Function<Integer, Integer> f = example::compareTo;

        assertThat(f.apply(2), is(-1));

        Function<Number, String> numberToStringFunction = Number::toString;
        assertThat(numberToStringFunction.apply(1), is("1"));
    }
}
