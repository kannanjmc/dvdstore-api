package com.scottwseo.dvdstore.util;

import com.scottwseo.commons.util.Features;
import com.scottwseo.dvdstore.config.Configs;
import com.scottwseo.commons.rest.featureflag.FeatureManagerProviderBase;
import com.scottwseo.dvdstore.config.PostgreSQLDatabase;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;

public class DVDStoreFeatureManagerProvider extends FeatureManagerProviderBase {

    public DVDStoreFeatureManagerProvider() {
        super();
    }

    public Class getFeatureClass() {
        return Features.class;
    }

    public synchronized DataSource getDataSource() {

        if (PostgreSQLDatabase.check()) {
            String url = Configs.DB_URL.getString();
            String user = Configs.DB_USER.getString();
            String password = Configs.DB_PWD.getString();

            PGPoolingDataSource source = new PGPoolingDataSource();
            source.setUrl(url);
            source.setUser(user);
            source.setPassword(password);
            return source;
        }
        else {
            return null;
        }

    }

}