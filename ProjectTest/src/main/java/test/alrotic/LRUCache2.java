package test.alrotic;

import java.util.LinkedHashMap;
import java.util.Map;


//LRU
public class LRUCache2<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4213158150712542024L;

	private final int MAX_CACHE_SIZE;

	public LRUCache2(int cacheSize) {
		//+1用來防止map自動擴充
		super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
		MAX_CACHE_SIZE = cacheSize;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > MAX_CACHE_SIZE;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<K, V> entry : entrySet()) {
			sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
		}
		return sb.toString();
	}

	
	
}
