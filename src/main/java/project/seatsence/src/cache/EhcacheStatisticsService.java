package project.seatsence.src.cache;

import org.ehcache.CacheManager;
import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.core.statistics.CacheStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EhcacheStatisticsService {
    private final CacheManager cacheManager;
    private final StatisticsService statisticsService;

    @Autowired
    public EhcacheStatisticsService(
            CacheManager cacheManager, StatisticsService statisticsService) {
        this.cacheManager = cacheManager;
        this.statisticsService = statisticsService;
    }

    public CacheStatistics getCacheStatistics(String cacheName) {
        return statisticsService.getCacheStatistics(cacheName);
    }

    // 히트율 조회
    public long getCacheHits(String cacheName) {
        CacheStatistics stats = getCacheStatistics(cacheName);
        return stats == null ? 0 : stats.getCacheHits();
    }

    // 미스율 조회
    public long getCacheMisses(String cacheName) {
        CacheStatistics stats = getCacheStatistics(cacheName);
        return stats == null ? 0 : stats.getCacheMisses();
    }
}
