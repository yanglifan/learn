package yanglifan.learn.core.exam;

public class OverloadDemo {
    public static void call(Parent parent) {
        System.out.println("call with parent");
    }

    public static void call(Child child) {
        System.out.println("call with child");
    }

    public static void main(String[] args) {
        Parent obj = new Child();
        call(obj);
    }

    static class Parent {

    }

    static class Child extends Parent {

    }
}
