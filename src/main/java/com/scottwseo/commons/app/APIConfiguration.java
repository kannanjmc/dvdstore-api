package com.scottwseo.commons.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scottwseo.commons.util.Config;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class APIConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {

        if (!Config.check()) {
            throw new RuntimeException("Configuration is not initialized");
        }

        database.setUrl(Config.DB_URL.getString());
        database.setUser(Config.DB_USER.getString());
        database.setPassword(Config.DB_PWD.getString());

        return database;
    }

}
