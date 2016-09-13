package com.scottwseo.commons.util;

import com.scottwseo.commons.togglz.FeatureManagerProviderBase;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;

public class CommonsFeatureManagerProvider extends FeatureManagerProviderBase {

    public CommonsFeatureManagerProvider() {
        super();
    }

    public Class getFeatureClass() {
        return Features.class;
    }

    public synchronized DataSource getDataSource() {
        String url = Configs.DB_URL.getString();
        String user = Configs.DB_USER.getString();
        String password = Configs.DB_PWD.getString();

        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setUrl(url);
        source.setUser(user);
        source.setPassword(password);
        return source;
    }

}