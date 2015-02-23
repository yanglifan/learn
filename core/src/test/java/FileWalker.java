import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FileWalker {
    private Queue<File> fileQueue = new LinkedList<>();

    public void walk(File root) {
        if (root.listFiles() == null) {
            return;
        }

        fileQueue.addAll(Arrays.asList(root.listFiles()));

        int numberOfFile = 0;
        File file;
        while ((file = fileQueue.poll()) != null) {
            if (file.isDirectory()) {
                if (file.listFiles() != null)
                    fileQueue.addAll(Arrays.asList(file.listFiles()));
            }

            numberOfFile++;
        }
        System.out.println(numberOfFile);
    }

    public static void main(String[] args) {
        new FileWalker().walk(new File("."));
    }
}
