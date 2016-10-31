package com.scottwseo.commons.guice;

import com.github.kristofa.brave.Brave;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.scottwseo.commons.app.APIConfiguration;
import com.smoketurner.dropwizard.zipkin.client.ZipkinClientBuilder;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;
import javax.ws.rs.client.Client;

public class ServiceModule extends AbstractModule {

    protected APIConfiguration configuration;
    protected Environment environment;
    private DBI dbi;
    private Brave brave;
    private Client client;

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

    @Provides
    public DBI provideDBI() {
        if (dbi == null) {
            DBIFactory factory = new DBIFactory();
            this.dbi = factory.build(environment, configuration.getDataSourceFactory(), "dbi");
        }

        return dbi;
    }

    @Provides
    public Brave provideBrave() {
        if (this.brave == null) {
            this.brave = configuration.getZipkinFactory().build(environment);
        }

        return this.brave;
    }

    @Provides
    public Client provideClient() {
        if (this.client == null) {
            this.client = new ZipkinClientBuilder(environment, this.provideBrave())
                    .build(configuration.getZipkinClient());
        }

        return this.client;
    }

}
