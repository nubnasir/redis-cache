package com.nasir.rediscache.controller;

import com.nasir.rediscache.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @CachePut(value = "products", key = "#product.id")
    @PostMapping("")
    public Product create(@RequestBody Product product) {
        log.info("CREATE ID: {} view: {}", product.getId(), product.getView());
        return product;
    }

    @Cacheable(value = "products", key = "#productId")
    @GetMapping("/{productId}")
    public Product get(@PathVariable String productId) {
        Product product = new Product(productId, "Product " + productId, new Double("10"), getViews(), "Description " + productId);
        log.info("GET ID: {} view: {}", product.getId(), product.getView());
        return product;
    }

    @CacheEvict(value = "products", allEntries = true)
    @DeleteMapping("/{productId}")
    public Product delete(@PathVariable String productId) {
        Product product = new Product(productId, "Product " + productId, new Double("10"), getViews(), "Description " + productId);
        log.info("DELETE ID: {} view: {}", product.getId(), product.getView());
        return product;
    }

    private long getViews(){
        return (long) (Math.random()*(2000 - 1000)) + 1000;
    }
}
