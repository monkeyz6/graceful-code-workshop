package com.xk857.main.boot.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalProductCache {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    public void put(String key, Object value) {cache.put(key, value);}    
    public Object get(String key) {return cache.get(key);}                
    public void invalidate(String key) {cache.remove(key);}               
    public int size() {return cache.size();}
}

