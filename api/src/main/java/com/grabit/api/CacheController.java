package com.grabit.api;

import com.grabit.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    CacheService cacheService;

    @GetMapping(value = "/cache-data/{cache_name}")
    public void checkCache(@PathVariable("cache_name") String cacheName){
//        cacheService.printCacheContent(cacheName);
        cacheService.printCacheContentFromRedis(cacheName);
    }
}
