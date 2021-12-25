package vip.testops.hammer.cache;

import java.util.HashMap;
import java.util.Map;

public class TestClassCache {
    private ThreadLocal<Map<String, Object>> cache = ThreadLocal.withInitial(HashMap::new);


    public void add(String key, Object value) {
        cache.get().put(key, value);
    }

    public Object get(String key) {
        return cache.get().get(key);
    }
}
