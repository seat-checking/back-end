package project.seatsence.global.config;

import java.util.concurrent.TimeUnit;
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
import org.springframework.cache.annotation.EnableCaching;
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
    public CacheManager ehcacheManager(StatisticsService statisticsService) {
        CacheManager cacheManager =
                CacheManagerBuilder.newCacheManagerBuilder()
                        .using(statisticsService)
                        .withCache(
                                cacheName,
                                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                                Long.class, // 키 타입
                                                project.seatsence.src.store.dto.response.admin.space
                                                        .StoreSpaceSeatResponse.class, // 값 타입
                                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                                        .heap(
                                                                20000,
                                                                EntryUnit
                                                                        .ENTRIES) // 힙 메모리에 10개의 엔트리
                                                        // 저장
                                                        .offheap(
                                                                10,
                                                                MemoryUnit.MB) // 오프힙 메모리에 1MB 저장
                                                )
                                        .withExpiry(
                                                Expirations.timeToLiveExpiration(
                                                        Duration.of(
                                                                3600,
                                                                TimeUnit.SECONDS))) // TTL 설정: 5분
                                )
                        .build(true);

        return cacheManager;
    }
}
