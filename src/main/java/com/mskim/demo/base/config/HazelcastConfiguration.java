package com.mskim.demo.base.config;

import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class HazelcastConfiguration {

    @Value("${services.hazelcast.cluster-name}")
    private String clusterName;

    @Value("${services.hazelcast.address}")
    private String address;

    @Value("${services.hazelcast.port}")
    private String port;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setClusterName(clusterName);

        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.getInterfaces().addInterface(address);

        MapConfig mapPostConfig = new MapConfig()
                .setName("post")
                .setTimeToLiveSeconds(600);

        config.addMapConfig(mapPostConfig);

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }


}
