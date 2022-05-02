package com.nasir.rediscache.controller;

import com.nasir.rediscache.RedisTemplateHelper;
import com.nasir.rediscache.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v2/products")
public class RedisTemplateController {

    private final RedisTemplateHelper redisTemplate;

    public RedisTemplateController(RedisTemplateHelper redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("")
    public Product create(@RequestBody Product product) {
        log.info("CREATE ID: {} view: {}", product.getId(), product.getView());
        redisTemplate.store(product.getId(), product);
        return product;
    }

    @GetMapping("/{productId}")
    public Product get(@PathVariable String productId) {
        Product product = (Product) redisTemplate.fetch(productId);
        if(product != null){
            return product;
        }
        product = new Product(productId, "Product " + productId, new Double("10"), getViews(), "Description " + productId);
        log.info("GET ID: {} view: {}", product.getId(), product.getView());
        return product;
    }

    @DeleteMapping("/{productId}")
    public Product delete(@PathVariable String productId) {
        Product product = new Product(productId, "Product " + productId, new Double("10"), getViews(), "Description " + productId);
        redisTemplate.delete(productId);
        log.info("DELETE ID: {} view: {}", product.getId(), product.getView());
        return product;
    }

    private long getViews(){
        return (long) (Math.random()*(2000 - 1000)) + 1000;
    }
}
