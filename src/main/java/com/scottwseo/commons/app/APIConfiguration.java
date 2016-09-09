package com.scottwseo.commons.app;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class APIConfiguration extends Configuration {

    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {

        database.setDriverClass("org.postgresql.Driver");
        database.setUser("");

        return database;
    }

}
