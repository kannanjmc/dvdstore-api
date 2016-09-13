package com.scottwseo.commons.togglz;

import com.scottwseo.commons.util.Configs;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.cache.CachingStateRepository;
import org.togglz.core.repository.jdbc.JDBCStateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.spi.FeatureManagerProvider;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

import javax.sql.DataSource;

public abstract class FeatureManagerProviderBase implements FeatureManagerProvider {

    private static FeatureManager featureManager;

    private StateRepository stateRepository;

    public FeatureManagerProviderBase() {
        DataSource dataSource = getDataSource();

        if (dataSource != null && Configs.DB_URL.isProvided()) {
            this.stateRepository = new JDBCStateRepository(dataSource);
        } else {
            this.stateRepository = new InMemoryStateRepository();
        }
    }

    @Override
    public int priority() {
        return 30;
    }

    @Override
    public synchronized FeatureManager getFeatureManager() {

        if (featureManager == null) {
            featureManager = new FeatureManagerBuilder()
                    .featureEnum(getFeatureClass())
                    .stateRepository(new CachingStateRepository(stateRepository, 60 * 1000))
                    .userProvider(new UserProvider() {
                        @Override
                        public FeatureUser getCurrentUser() {
                            return new SimpleFeatureUser("admin", true);
                        }
                    })
                    .build();
        }

        return featureManager;
    }

    public abstract Class getFeatureClass();

    public abstract DataSource getDataSource();

}