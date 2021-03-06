package com.scottwseo.commons.rest.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smoketurner.dropwizard.zipkin.ZipkinFactory;
import com.smoketurner.dropwizard.zipkin.client.ZipkinClientConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public abstract class APIConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("jerseyClientConfiguration")
    private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();

    @Valid
    @NotNull
    @JsonProperty("zipkin")
    private ZipkinFactory zipkin;

    @Valid
    @NotNull
    private final ZipkinClientConfiguration zipkinClient = new ZipkinClientConfiguration();


    public DataSourceFactory getDataSourceFactory() {

        database.setUrl(getConfig("db.url"));
        database.setUser(getConfig("db.user"));
        database.setPassword(getConfig("db.pwd"));

        return database;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return this.jerseyClientConfiguration;
    }

    public ZipkinFactory getZipkinFactory() {
        return zipkin;
    }

    @JsonProperty
    public ZipkinClientConfiguration getZipkinClient() {
        return zipkinClient;
    }

    protected abstract String getConfig(String name);

}
