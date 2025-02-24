package com.mskim.demo.base.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class HazelcastConfiguration {

    @Value("${hazelcast.cluster-name}")
    private String clusterName;

    @Value("${hazelcast.address}")
    private String address;

    @Value("${hazelcast.port}")
    private String port;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setClusterName(clusterName);

        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.getInterfaces().addInterface(address);

        MapConfig mapConfig = new MapConfig()
                .setName("postList")
                .setTimeToLiveSeconds(3600);

        config.addMapConfig(mapConfig);

        return Hazelcast.newHazelcastInstance(config);
    }


}
