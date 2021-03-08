package com.sample.multitenant.mongo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config.multitenant.mongo")
public class MultiTenantMongoProperties {

    private List<TenantProperties> properties;

    @Getter
    @Setter
    public static class TenantProperties {
        private String tenant;
        private MongoProperties properties;
    }
}
