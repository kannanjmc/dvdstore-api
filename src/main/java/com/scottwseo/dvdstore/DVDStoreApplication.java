package com.scottwseo.dvdstore;

import com.github.kristofa.brave.Brave;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.netflix.config.ConfigurationManager;
import com.scottwseo.commons.app.APIApplication;
import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.commons.exception.UnrecognizedPropertyExceptionMapper;
import com.scottwseo.commons.health.DummyHealthCheck;
import com.scottwseo.commons.logging.LogEndPoint;
import com.scottwseo.commons.resources.HelpResource;
import com.scottwseo.commons.resources.StartupCheckListResource;
import com.scottwseo.commons.util.Configs;
import com.scottwseo.commons.util.EnvVariables;
import com.scottwseo.commons.util.PostgreSQLDatabase;
import com.scottwseo.dvdstore.guice.DVDStoreServiceModule;
import com.scottwseo.dvdstore.resources.CategoryResource;
import com.scottwseo.dvdstore.resources.CustomersResource;
import com.scottwseo.dvdstore.resources.OrdersResource;
import com.scottwseo.dvdstore.resources.ProductsResource;
import com.smoketurner.dropwizard.zipkin.rx.BraveRxJavaSchedulersHook;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import rx.plugins.RxJavaPlugins;

import static com.scottwseo.commons.util.ConfigUtil.isRequired;
import static com.scottwseo.commons.util.LogUtil.info;
import static com.scottwseo.commons.util.LogUtil.warn;

public class DVDStoreApplication extends APIApplication {

    public static void main(String[] args) throws Exception {
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");
        if (args == null || args.length == 0) {
            args = new String[] {"server", "dvdstore-api/conf/dvdstore-api.yml"};
        }
        new DVDStoreApplication().run(args);
    }

    private Injector injector;

    private Environment environment;

    @Override
    public void initialize(final Bootstrap<APIConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(final APIConfiguration configuration,
                    final Environment environment) {

        this.environment = environment;

        String applicationContextPath = applicationContextPath(configuration);

        if (EnvVariables.check() && Configs.check() && PostgreSQLDatabase.check()) {

            this.injector = Guice.createInjector(new DVDStoreServiceModule(configuration, environment));

            final Brave brave = instanceOf(Brave.class);

            RxJavaPlugins.getInstance()
                    .registerSchedulersHook(new BraveRxJavaSchedulersHook(brave));

            registerResource(new HelpResource(applicationContextPath, getName(), getAppVersion()));

            registerResource(instanceOf(CategoryResource.class));

            registerResource(instanceOf(ProductsResource.class));

            registerResource(instanceOf(OrdersResource.class));

            registerResource(instanceOf(CustomersResource.class));

            environment.jersey().register(new UnrecognizedPropertyExceptionMapper());

            info("server.startup.completed", "");
        }
        else {
            registerResource(new StartupCheckListResource());

            environment.healthChecks().register("dummy", new DummyHealthCheck());

            StringBuilder s = new StringBuilder();
            if (!EnvVariables.check()) {
                for (EnvVariables env : EnvVariables.values()) {
                    if (isRequired(env) && !env.isProvided()) {
                        s.append("env [" + env.key() + "] missing ");
                    }
                }
            }

            if (!Configs.check()) {
                for (Configs config : Configs.values()) {
                    if (isRequired(config) && !config.isProvided()) {
                        s.append("config [" + config.key() + "] missing ");
                    }
                }
            }
            else if (!PostgreSQLDatabase.check()) {
                s.append("PostgreSQL database connection failure.");
                s.append("url:" + PostgreSQLDatabase.url());
            }

            warn("prelaunch.check.failed", s.toString());
        }

        websocket.addEndpoint(LogEndPoint.class);
    }

    @Override
    public String getName() {
        return ConfigurationManager.getConfigInstance().getString(Configs.APP_NAME.key(), "DVD Store API");
    }

    @Override
    protected String getAppVersion() {
        return getAppVersion("app.version", "v1.0.0");
    }

    private <T> T instanceOf(Class clazz) {
        return (T) injector.getInstance(clazz);
    }

    private void registerResource(Object o) {
        this.environment.jersey().register(o);
    }

}
