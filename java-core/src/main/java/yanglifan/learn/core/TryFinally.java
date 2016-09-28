package yanglifan.learn.core;

public class TryFinally {
    public static void main(String[] args) {
        System.out.println(what());
    }

    static boolean what() {
        try {
            return false;
        } finally {
            return true;
        }
    }
}
