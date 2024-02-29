package project.seatsence.global.config;

import java.net.URI;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfiguration {
    @Bean
    public JCacheCacheManager jCacheCacheManager() throws Exception {
        CacheManager cacheManager = cacheManager();
        return new JCacheCacheManager(cacheManager);
    }

    private CacheManager cacheManager() throws Exception {
        // EhcacheCachingProvider를 사용하여 CachingProvider 인스턴스를 가져옵니다.
        CachingProvider provider =
                Caching.getCachingProvider(EhcacheCachingProvider.class.getName());
        // 클래스 패스 상의 ehcache.xml 구성 파일을 참조하는 URI를 생성합니다.
        URI ehcacheConfigUri = getClass().getResource("/ehcache.xml").toURI();

        CacheManager cacheManager =
                provider.getCacheManager(ehcacheConfigUri, getClass().getClassLoader());
        return cacheManager;
    }
}
