package com.jarekjal.cbdemo.config;

import com.couchbase.client.java.Cluster;
import com.couchbase.transactions.Transactions;
import com.couchbase.transactions.config.TransactionConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableCouchbaseRepositories(basePackages = "com.jarekjal.cbdemo.repository")
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${app.couchbase.url}")
    private String url;

    @Value("${app.couchbase.user}")
    private String userName;

    @Value("${app.couchbase.password}")
    private String password;

    @Value("${app.couchbase.bucket}")
    private String bucket;

    @Override
    public String getConnectionString() {
        return url;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucket;
    }

    @Bean
    public Transactions transactions(final Cluster couchbaseCluster) {
        return Transactions.create(couchbaseCluster, TransactionConfigBuilder.create()
                .expirationTime(Duration.of(600, ChronoUnit.SECONDS)).build());
    }
}
