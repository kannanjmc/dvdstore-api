package com.scottwseo.commons.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scottwseo.commons.util.Configs;
import com.smoketurner.dropwizard.zipkin.ZipkinFactory;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class APIConfiguration extends Configuration {

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

    public DataSourceFactory getDataSourceFactory() {

        if (!Configs.check()) {
            throw new RuntimeException("Configuration is not initialized");
        }

        database.setUrl(Configs.DB_URL.getString());
        database.setUser(Configs.DB_USER.getString());
        database.setPassword(Configs.DB_PWD.getString());

        return database;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return this.jerseyClientConfiguration;
    }

    public ZipkinFactory getZipkinFactory() {
        return zipkin;
    }

}
