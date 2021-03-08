package com.sample.multitenant.mongo.config;

import com.mongodb.client.MongoDatabase;
import com.sample.multitenant.mongo.context.TenantContext;
import com.sample.multitenant.mongo.exception.TenantNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
public class MultiTenantMongoDbFactory extends SimpleMongoClientDatabaseFactory {

    public static final String DEFAULT_DB_INSTACE = "test";
    private final MultiTenantMongoConfig multiTenantMongoConfig;

    public MultiTenantMongoDbFactory(final MultiTenantMongoConfig multiTenantMongoConfig, final MultiTenantMongoConfig.TenantMongoClient dtMongoClient) {
        super(dtMongoClient.getMongoClient(), dtMongoClient.getDatabase());
        this.multiTenantMongoConfig = multiTenantMongoConfig;
    }

    @Override
    public MongoDatabase getMongoDatabase() throws DataAccessException {
        final String tenant = TenantContext.getTenantId();
        MongoDatabase database = null;
        if (tenant != null) {
            final MultiTenantMongoConfig.TenantMongoClient dtMongoClient = multiTenantMongoConfig.getMultiTenantConfig().get(tenant);
            if (dtMongoClient == null) {
                throw new TenantNotFoundException("Tenant " + tenant + " is not configured");
            }
            database = dtMongoClient.getMongoClient().getDatabase(dtMongoClient.getDatabase());
        } else {
            database = getMongoClient().getDatabase(DEFAULT_DB_INSTACE);
        }
        return database;
    }

    @Override
    public void destroy() throws Exception {
        multiTenantMongoConfig.getMultiTenantConfig().values().forEach(mongo -> mongo.getMongoClient().close());
    }
}
