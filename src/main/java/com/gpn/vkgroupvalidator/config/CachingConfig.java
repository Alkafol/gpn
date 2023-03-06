package com.gpn.vkgroupvalidator.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("user-group");
    }

    @CacheEvict(value = "user-group", allEntries = true)
    @Scheduled(fixedRate = 24 * 3600 * 1000) // 24 hours
    public void requestsCache() {
        System.out.println("Clear cache");
    }
}

