package com.mskim.demo.base.config;


import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@RequiredArgsConstructor
public class MongoConfiguration {

    @Value("${services.mongodb.host}")
    private String host;

    @Value("${services.mongodb.port}")
    private String port;

    @Value("${services.mongodb.database}")
    private String database;

    @Value("${services.mongodb.username}")
    private String username;

    @Value("${services.mongodb.password}")
    private String password;

    private int connectTimeoutMS = 3000;
    private int socketTimeoutMS = 1000 * 60 * 5;
    private int minPoolSize = 5;
    private int maxPoolSize = 200;

    @Bean
    public MongoClient mongoClient() {
        String connectUrl = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + database +
                "?authSource=admin" +
                "&connectTimeoutMS=" + connectTimeoutMS +
                "&socketTimeoutMS=" + socketTimeoutMS +
                "&minPoolSize=" + minPoolSize +
                "&maxPoolSize=" + maxPoolSize +
                "&compressors=zstd,snappy,zlib";

        return MongoClients.create(connectUrl);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient(), database));
    }
}
