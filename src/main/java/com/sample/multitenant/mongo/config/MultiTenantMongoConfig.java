package com.sample.multitenant.mongo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.TreeMap;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
@RequiredArgsConstructor
public class MultiTenantMongoConfig {
    @Getter
    @AllArgsConstructor
    @ToString
    public static class TenantMongoClient {
        private MongoClient mongoClient;
        private String database;
    }

    private TreeMap<String, TenantMongoClient> multiTenantConfig;
    private final MultiTenantMongoProperties multiTenantMongoProperties;

    @PostConstruct
    public void multiTenantMongoConfig() {
        final List<MultiTenantMongoProperties.TenantProperties> multiTenantList = multiTenantMongoProperties.getProperties();
        multiTenantConfig = new TreeMap<>();

        for (final MultiTenantMongoProperties.TenantProperties multiTenant : multiTenantList) {
            final String connectionUri = multiTenant.getProperties().getUri();
            final String host = multiTenant.getProperties().getHost();
            final Integer port = multiTenant.getProperties().getPort();
            MongoClient client;

            if (connectionUri != null) {
                client = MongoClients.create(connectionUri);
            } else if (host != null && port != null) {
                final String connection = "mongodb://" + host + ":" + port + "/";
                client = MongoClients.create(connection);
            } else {
                throw new RuntimeException("At-least one of the config properties is required [uri | host & port]");
            }
            final String database = multiTenant.getProperties().getDatabase();
            final TenantMongoClient dtMongoClient = new TenantMongoClient(client, database);
            this.multiTenantConfig.put(multiTenant.getTenant(), dtMongoClient);
        }
    }

    public TreeMap<String, TenantMongoClient> getMultiTenantConfig() {
        return multiTenantConfig;
    }

    @PreDestroy
    public void destroy() {
        multiTenantConfig.values().forEach(mongo -> mongo.getMongoClient().close());
    }
}
