package com.scottwseo.commons.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.commons.service.CategoryService;
import com.scottwseo.commons.service.CategoryServiceImpl;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

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
        bind(CategoryService.class).to(CategoryServiceImpl.class);
    }

    @Provides
    public DataSource getDataSource() {
        ManagedDataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), "datasource-" + System.currentTimeMillis());
        environment.lifecycle().manage(dataSource);
        return dataSource;
    }

    @Provides
    public DBI provideDBI() {
        DBIFactory factory = new DBIFactory();
        return factory.build(environment, configuration.getDataSourceFactory(), "dbi");
    }

}
