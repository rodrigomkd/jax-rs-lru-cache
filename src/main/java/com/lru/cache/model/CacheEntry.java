package com.lru.cache.model;

public class CacheEntry {
    private String key;
    private String value;

    public CacheEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // Getters and setters
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}