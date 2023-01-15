package org.pt.flightbooking.core.configuration.cache;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.pt.flightbooking.domain.dto.response.FlightHeaderResponseDto;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.interceptor.KeyGenerator;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager EhCacheManager() {

        final CacheConfiguration<String, FlightHeaderResponseDto> cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, FlightHeaderResponseDto.class,
        ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(10, MemoryUnit.MB).build())
                            .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(10)))
                            .build();

        final CachingProvider cachingProvider = Caching.getCachingProvider();
        final CacheManager cacheManager = cachingProvider.getCacheManager();

        final javax.cache.configuration.Configuration<String, FlightHeaderResponseDto> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
        cacheManager.createCache("cacheStore", configuration);
        return cacheManager;
    }
    @Bean
    public KeyGenerator customKeyGenerator() {
        return (o, method, objects) -> {
            final StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (final Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}