package yanglifan.learn.core.exam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapDemo {
    private Map<String, String> map = new ConcurrentHashMap<>();

    public void putIfAbsent(String key, String value) {
        if (!map.containsKey(key)) {
            map.put(key, value);
        }
    }
}
