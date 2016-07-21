package com.yougou.api.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * LRU算法缓存Map类
 * 
 * @author 杨梦清
 *
 */
public class LRUMemoryCache {

	private static final LRUMemoryCache INSTANCE = new LRUMemoryCache();

	private Map<String, Object> cacheMap = Collections.synchronizedMap(new LRUMemoryCacheMap<String, Object>(Short.MAX_VALUE));

	private LRUMemoryCache() {
		super();
	}

	public Object get(String key) {
		return cacheMap.get(key);
	}

	public void put(String key, Object value) {
		cacheMap.put(key, value);
	}

	public static LRUMemoryCache getInstance() {
		return LRUMemoryCache.INSTANCE;
	}

	public static class LRUMemoryCacheMap<K, V> extends LinkedHashMap<K, V> {

		private static final long serialVersionUID = -8491031640050134128L;

		private int _initialCapacity;

		protected LRUMemoryCacheMap(int initialCapacity) {
			super(initialCapacity);
			this._initialCapacity = initialCapacity;
		}

		@Override
		protected boolean removeEldestEntry(Entry<K, V> eldest) {
			return super.size() > this._initialCapacity;
		}
	}
}
