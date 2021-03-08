package com.sample.multitenant.mongo.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
@ConditionalOnProperty(value = "config.multitenant.mongo.enabled", havingValue = "true")
@EnableConfigurationProperties(value = MultiTenantMongoProperties.class)
@AutoConfigureBefore(MongoAutoConfiguration.class)
@Configuration
@EnableMongoRepositories(basePackages = {"com.sample"})
@EntityScan(basePackages = {"com.sample"})
public class MultiTenantMongoAutoConfiguration {

    @Bean
    public MultiTenantMongoConfig multiTenantMongoConfig(final MultiTenantMongoProperties multiTenantMongoProperties) {
        return new MultiTenantMongoConfig(multiTenantMongoProperties);
    }

    @Bean
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory(final MultiTenantMongoConfig multiTenantMongoConfig) {
        final MultiTenantMongoConfig.TenantMongoClient tenantMongoClient = multiTenantMongoConfig.getMultiTenantConfig().firstEntry().getValue();
        return new MultiTenantMongoDbFactory(multiTenantMongoConfig, tenantMongoClient);
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate(final MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
