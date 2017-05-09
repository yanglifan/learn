package yanglifan.learn.core.exam;

import org.junit.Test;

public class ClassInitDemo {
    static class Parent {
        static {
            System.out.println("Parent.staticBlock");
        }

        public Parent() {
            System.out.println("Parent.constructor");
        }
    }

    static class Child extends Parent {
        static {
            System.out.println("Child.staticBlock");
        }

        public Child() {
            System.out.println("Child.constructor");
        }
    }

    @Test
    public void child_constructor_run() {
        new Child();
    }
}
