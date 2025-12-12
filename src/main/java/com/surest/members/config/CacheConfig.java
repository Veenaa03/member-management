package com.surest.members.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Using simple in-memory cache (spring.cache.type = simple).
}
