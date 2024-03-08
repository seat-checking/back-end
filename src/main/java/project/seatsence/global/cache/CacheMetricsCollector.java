package project.seatsence.global.cache;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CacheMetricsCollector {
    private final EhcacheStatisticsService ehcacheStatisticsService;
    private final MeterRegistry meterRegistry;

    public CacheMetricsCollector(
            EhcacheStatisticsService ehcacheStatisticsService, MeterRegistry meterRegistry) {
        this.ehcacheStatisticsService = ehcacheStatisticsService;
        this.meterRegistry = meterRegistry;

        // 캐시 히트 수치에 대한 게이지 등록
        Gauge.builder(
                        "cache.hits",
                        ehcacheStatisticsService,
                        statsService -> statsService.getCacheHits("tablesAndChairsPerSpace"))
                .description("Number of cache hits")
                .register(meterRegistry);

        // 캐시 미스 수치에 대한 게이지 등록
        Gauge.builder(
                        "cache.misses",
                        ehcacheStatisticsService,
                        statsService -> statsService.getCacheMisses("tablesAndChairsPerSpace"))
                .description("Number of cache misses")
                .register(meterRegistry);
    }
}
