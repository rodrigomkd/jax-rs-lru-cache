package com.lru.cache.service;

import java.util.List;

import com.lru.cache.model.CacheEntry;
import com.lru.cache.model.LruCache;

public class CacheService {
    private static final int MAX_SIZE = 3;
    private static LruCache<String, String> cache = new LruCache<>(MAX_SIZE);

    public static synchronized List<CacheEntry> getCache() {
        return cache.getValues();
    }

    public static synchronized CacheEntry get(String name) {
        return new CacheEntry(name, cache.get(name));
    }

    public static synchronized boolean update(String key, String value) {
        if(! cache.containsKey(key)) {
            return false;
        }
        cache.put(key, value);
        System.out.println("map: " + cache);
        return true;
    }

    public static synchronized void delete(String key) {
        cache.removeKey(key);
    }

    public static synchronized void clear() {
        cache = new LruCache<>(MAX_SIZE);
    }
}
