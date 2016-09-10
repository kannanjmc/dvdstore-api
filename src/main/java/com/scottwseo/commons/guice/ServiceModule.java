package com.scottwseo.commons.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.scottwseo.commons.app.APIConfiguration;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Environment;

import javax.sql.DataSource;

public class ServiceModule extends AbstractModule {

    private APIConfiguration configuration;
    private Environment environment;

    public ServiceModule(APIConfiguration configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    protected void configure() {
    }

    @Provides
    public DataSource getDataSource() {
        ManagedDataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), "datasource-" + System.currentTimeMillis());
        environment.lifecycle().manage(dataSource);
        return dataSource;
    }

}
