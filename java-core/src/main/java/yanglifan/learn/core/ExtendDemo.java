package yanglifan.learn.core;

public class ExtendDemo {
}

interface Base {

}
interface A extends Base {
    A method();
}

interface B extends Base {
    B method();
}

interface AB extends A, B {
    AB method();
}