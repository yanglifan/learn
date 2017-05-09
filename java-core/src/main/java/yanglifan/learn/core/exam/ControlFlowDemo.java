package yanglifan.learn.core.exam;

import org.junit.Test;

public class ControlFlowDemo {

    @Test
    public void switch_demo() {
        int i = 0;
        switch (i) {
            case 0:
                System.out.println("i is 0");
            case 1:
                System.out.println("i is 1");
            case 2:
                System.out.println("i is 2");
            default:
                System.out.println("i is a negative integer");
        }
    }
}
