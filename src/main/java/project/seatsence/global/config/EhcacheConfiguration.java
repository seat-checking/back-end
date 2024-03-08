package project.seatsence.global.config;

import java.util.concurrent.TimeUnit;
import javax.cache.Caching;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.internal.statistics.DefaultStatisticsService;
import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfiguration {
    final String cacheName = "tablesAndChairsPerSpace";

    @Bean
    public StatisticsService statisticsService() {
        return new DefaultStatisticsService();
    }

    @Bean
    public JCacheCacheManager jCacheCacheManager(javax.cache.CacheManager ehcacheManager) {
        return new JCacheCacheManager(ehcacheManager);
    }

    @Bean
    public javax.cache.CacheManager cacheManager(StatisticsService statisticsService) {
        CacheManager ehCacheManager =
                CacheManagerBuilder.newCacheManagerBuilder()
                        .using(statisticsService)
                        .withCache(
                                cacheName,
                                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                                Long.class,
                                                project.seatsence.src.store.dto.response.admin.space
                                                        .StoreSpaceSeatResponse.class,
                                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                                        .heap(20000, EntryUnit.ENTRIES)
                                                        .offheap(10, MemoryUnit.MB))
                                        .withExpiry(
                                                Expirations.timeToLiveExpiration(
                                                        Duration.of(3600, TimeUnit.SECONDS))))
                        .build(true);

        ehCacheManager.init();

        EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();
        return provider.getCacheManager(provider.getDefaultURI(), provider.getDefaultClassLoader());
    }
}
