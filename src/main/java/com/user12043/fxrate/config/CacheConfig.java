package com.user12043.fxrate.config;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * Initializes Cache Manager for some services (Marked with @{@link org.springframework.cache.annotation.Cacheable})
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configure EHCACHE library to use
     *
     * @return EHCACHE manager
     */
    @Bean
    public CacheManager ehCacheManager() {
        final CachingProvider cachingProvider = Caching.getCachingProvider();
        EhcacheCachingProvider provider = (EhcacheCachingProvider) cachingProvider;
        return provider.getCacheManager();
    }
}
