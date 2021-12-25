package vip.testops.hammer.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试类专用缓存，采用线程独立存储方式，所以当存在多线程运行测试用例时，缓存并不通用，线程之间互不影响
 * @author Louis
 */
public class TestClassCache {
    /**
     * 线程独立缓存空间
     */
    private final ThreadLocal<Map<String, Object>> cache = ThreadLocal.withInitial(HashMap::new);

    /**
     * 添加key-value到缓存
     * @param key key
     * @param value value
     */
    public void add(String key, Object value) {
        cache.get().put(key, value);
    }

    /**
     * 从缓存中根据给定的key取出对应的值
     * @param key key
     * @return key对应的value
     */
    public Object get(String key) {
        return cache.get().get(key);
    }
}
