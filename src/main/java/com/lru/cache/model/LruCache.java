package com.lru.cache.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LruCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        if (!map.containsKey(key)) return null;
        Node<K, V> node = map.get(key);
        moveToFront(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            moveToFront(node);
        } else {
            if (map.size() >= capacity) {
                evictLeastRecentlyUsed();
            }
            Node<K, V> newNode = new Node<>(key, value);
            addToFront(newNode);
            map.put(key, newNode);
        }
    }

    private void moveToFront(Node<K, V> node) {
        remove(node);
        addToFront(node);
    }

    private void addToFront(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void evictLeastRecentlyUsed() {
        Node<K, V> lru = tail.prev;
        remove(lru);
        map.remove(lru.key);
    }

    public List<CacheEntry> getValues() {
        List<CacheEntry> entries = new ArrayList<>();
        this.map.entrySet().forEach(node -> {
            entries.add(new CacheEntry(node.getKey().toString(), node.getValue().toString()));
        });

        return entries;
    }

    public boolean removeKey(String key) {
        Node<K, V> value = this.map.get(key);
        if(value == null) {
            return false;
        }

        this.map.remove(key);
        remove(value);
        return true;
    }

    public boolean containsKey(String key) {
        return this.map.containsKey(key);
    }
}
