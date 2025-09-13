package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.cache.LocalProductCache;
import com.xk857.main.boot.model.Product;
import com.xk857.main.boot.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redisson/product")
@RequiredArgsConstructor
@Tag(name = "分布式集合/缓存演示", description = "Bloom、MapCache、Topic")
@WorkshopDemo(scene = "共享大脑&广播", focus = "RBloomFilter/RMapCache/RTopic")
public class ProductController {

    private final ProductService productService;
    private final LocalProductCache localCache;

    @Operation(summary = "从智能缓存获取商品")
    @GetMapping("/get")
    public Result<Product> get(@RequestParam String id) {
        Product p = productService.getProduct(id);
        if (p != null) {
            localCache.put(id, p); // 写入本地缓存，供广播演示
        }
        return Result.success(p);
    }

    @Operation(summary = "更新价格并广播失效")
    @PostMapping("/updatePrice")
    public Result<Product> updatePrice(@RequestParam String id, @RequestParam double price) {
        return Result.success(productService.updatePrice(id, price));
    }

    @Operation(summary = "本地缓存查看")
    @GetMapping("/localCache")
    public Result<Object> local(@RequestParam String id) {
        return Result.success(localCache.get(id));
    }

    @Operation(summary = "布隆添加ID")
    @PostMapping("/bloom/add")
    public Result<Boolean> bloomAdd(@RequestParam String id) {
        return Result.success(productService.addToBloom(id));
    }

    @Operation(summary = "布隆检测ID")
    @GetMapping("/bloom/check")
    public Result<Boolean> bloomCheck(@RequestParam String id) {
        return Result.success(productService.bloomContains(id));
    }
}

