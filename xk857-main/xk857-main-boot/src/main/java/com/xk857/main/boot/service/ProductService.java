package com.xk857.main.boot.service;

import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 商品服务：Bloom守门人 + RMapCache 智能缓存 + Topic广播
 */
@Slf4j
@Service
@RequiredArgsConstructor
@WorkshopDemo(scene = "分布式集合/缓存/广播", focus = "RBloomFilter/RMapCache/RTopic")
public class ProductService {

    private final RedissonClient redissonClient;

    // 模拟数据库
    private final Map<String, Product> repository = new ConcurrentHashMap<>();

    private RMapCache<String, Product> productCache;
    private RBloomFilter<String> productFilter;
    private RMap<String, Double> priceMap;
    private RTopic topic;

    @PostConstruct
    public void init() {
        this.productCache = redissonClient.getMapCache("product_details_cache");
        this.priceMap = redissonClient.getMap("product:prices");
        this.productFilter = redissonClient.getBloomFilter("product_id_filter");
        this.productFilter.tryInit(1_000_000L, 0.0001);
        this.topic = redissonClient.getTopic("cache-invalidation-topic");
        // 预置两条数据
        putDb(new Product("p-123", "Phone", 6888.0, LocalDateTime.now()));
        putDb(new Product("p-124", "Pad", 3999.0, LocalDateTime.now()));
    }

    private void putDb(Product p) {
        repository.put(p.getId(), p);
        productFilter.add(p.getId());
        priceMap.put(p.getId(), p.getPrice());
    }

    public Product getProduct(String id) {
        // 布隆守门
        if (!productFilter.contains(id)) {
            return null; // 一定不存在
        }
        Product cached = productCache.get(id);
        if (cached != null) {
            return cached;
        }
        Product fromDb = repository.get(id);
        if (fromDb != null) {
            productCache.put(id, fromDb, 1, TimeUnit.HOURS, 30, TimeUnit.MINUTES);
        }
        return fromDb;
    }

    public boolean addToBloom(String id) {
        return productFilter.add(id);
    }

    public boolean bloomContains(String id) {
        return productFilter.contains(id);
    }

    public Product updatePrice(String id, double newPrice) {
        Product p = repository.get(id);
        if (p == null) return null;
        p.setPrice(newPrice);
        p.setUpdatedAt(LocalDateTime.now());
        repository.put(id, p);
        productCache.put(id, p, 1, TimeUnit.HOURS, 30, TimeUnit.MINUTES);
        priceMap.put(id, newPrice);
        // 广播本地缓存失效
        topic.publish("product:price_changed:" + id);
        return p;
    }
}

