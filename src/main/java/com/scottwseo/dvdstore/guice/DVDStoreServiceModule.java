package com.scottwseo.dvdstore.guice;

import com.google.common.base.MoreObjects;
import com.google.inject.name.Names;
import com.netflix.config.ConfigurationManager;
import com.scottwseo.dvdstore.config.Configs;
import com.scottwseo.commons.logging.ServerFactoryWrapper;
import com.scottwseo.commons.rest.app.APIConfiguration;
import com.scottwseo.commons.rest.guice.ServiceModule;
import com.scottwseo.commons.rest.resources.HelpResource;
import com.scottwseo.dvdstore.service.*;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.setup.Environment;
import org.apache.commons.io.IOUtils;

public class DVDStoreServiceModule extends ServiceModule {

    public DVDStoreServiceModule(APIConfiguration configuration, Environment environment) {
        super(configuration, environment);
    }

    @Override
    protected void configure() {
        bind(CategoryService.class).to(CategoryServiceImpl.class);

        bind(ProductsService.class).to(ProductsServiceImpl.class);

        bind(OrdersService.class).to(OrdersServiceImpl.class);

        bind(CustomersService.class).to(CustomersServiceImpl.class);

        bindConstant()
            .annotatedWith(Names.named("ApplicationContextPath"))
            .to(applicationContextPath());

        bindConstant()
                .annotatedWith(Names.named("AppVersion"))
                .to(getAppVersion());

        bindConstant()
                .annotatedWith(Names.named("AppName"))
                .to(getAppName());
    }

    protected String applicationContextPath() {
        ServerFactory server = configuration.getServerFactory();
        if (server instanceof DefaultServerFactory) {
            return ((DefaultServerFactory) server).getApplicationContextPath();
        }
        if (server instanceof ServerFactoryWrapper) {
            return ((DefaultServerFactory) ((ServerFactoryWrapper) server).getServerFactory()).getApplicationContextPath();
        }
        return null;
    }

    protected String getAppVersion() {

        try {
            ClassLoader classLoader =
                    MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(),
                            HelpResource.class.getClassLoader());

            return IOUtils.toString(classLoader.getResourceAsStream("app.version"));
        }
        catch(Exception ioe) {
            return "v1.0.0";
        }

    }

    public String getAppName() {
        return ConfigurationManager.getConfigInstance().getString(Configs.APP_NAME.key(), "DVD Store API");
    }

}
