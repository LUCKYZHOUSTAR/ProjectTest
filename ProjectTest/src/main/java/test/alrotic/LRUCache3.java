package test.alrotic;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache3<K, V> {

	private final int MAX_CACHE_SIZE;
	private final float DEFAULT_LOAD_FACTOR = 0.75f;

	LinkedHashMap<K, V> map;
	/** The lock protecting all mutators */
	transient final ReentrantLock lock = new ReentrantLock();

	public LRUCache3(int cacheSize) {
		MAX_CACHE_SIZE = cacheSize;
		// 根据cachesize和加载因此计算hashmap的capacity
		// +1确保当达到cachesize上限时，不会出发hashmap的扩容
		int capacity = (int) (Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTOR) + 1);
		map = new LinkedHashMap(capacity, DEFAULT_LOAD_FACTOR, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry eldest) {
				return size() > MAX_CACHE_SIZE;
			}
		};

	}

	public void put(K key, V value) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			map.put(key, value);
		} finally {
			lock.unlock();
		}
	}

	public synchronized V get(K key) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return map.get(key);
		} finally {
			lock.unlock();
		}
	}

}
