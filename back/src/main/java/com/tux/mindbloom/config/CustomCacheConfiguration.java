package com.tux.mindbloom.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableCaching
public class CustomCacheConfiguration {
  @Bean("ttl1h")
  public CacheManager timedCacheManager() {
    final CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCacheSpecification("expireAfterWrite=1h");
    final CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
    compositeCacheManager.setCacheManagers(Collections.singletonList(cacheManager));
    compositeCacheManager.setFallbackToNoOpCache(true);
    return compositeCacheManager;
  }
}
