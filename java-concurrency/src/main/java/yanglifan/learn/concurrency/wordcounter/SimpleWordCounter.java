package yanglifan.learn.concurrency.wordcounter;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleWordCounter {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
//        System.out.println(Paths.get("big.log").toFile().getAbsolutePath());
        BufferedReader reader = Files.newBufferedReader(Paths.get("small.log"), Charset.defaultCharset());
        String line;
        Map<String, AtomicInteger> wordCountMap = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            for (String word : words) {
                AtomicInteger count = wordCountMap.get(word);
                if (count == null) {
                    count = new AtomicInteger();
                    wordCountMap.put(word, count);
                }
                count.getAndIncrement();
            }
        }

        for (Map.Entry<String, AtomicInteger> entry : wordCountMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println((System.currentTimeMillis() - startTime) + "ms");
    }
}
