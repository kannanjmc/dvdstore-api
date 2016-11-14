package com.scottwseo.dvdstore;

import com.github.kristofa.brave.Brave;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottwseo.commons.config.ConfigManager;
import com.scottwseo.commons.logging.LogEndPoint;
import com.scottwseo.commons.logging.WebsocketBundle;
import com.scottwseo.commons.rest.app.APIConfiguration;
import com.scottwseo.commons.rest.auth.AuthenticationBundle;
import com.scottwseo.commons.rest.exception.UnrecognizedPropertyExceptionMapper;
import com.scottwseo.commons.rest.featureflag.TogglzBundle;
import com.scottwseo.commons.rest.health.DummyHealthCheck;
import com.scottwseo.commons.rest.resources.HelpResource;
import com.scottwseo.commons.rest.resources.StartupCheckListResource;
import com.scottwseo.commons.util.EnvUtil;
import com.scottwseo.dvdstore.config.Configs;
import com.scottwseo.dvdstore.config.EnvVariables;
import com.scottwseo.dvdstore.config.PostgreSQLDatabase;
import com.scottwseo.dvdstore.guice.DVDStoreServiceModule;
import com.scottwseo.dvdstore.guice.ErrorMessageBuilder;
import com.scottwseo.dvdstore.resources.CategoryResource;
import com.scottwseo.dvdstore.resources.CustomersResource;
import com.scottwseo.dvdstore.resources.OrdersResource;
import com.scottwseo.dvdstore.resources.ProductsResource;
import com.smoketurner.dropwizard.zipkin.ZipkinBundle;
import com.smoketurner.dropwizard.zipkin.ZipkinFactory;
import com.smoketurner.dropwizard.zipkin.rx.BraveRxJavaSchedulersHook;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import rx.plugins.RxJavaPlugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.info;
import static com.scottwseo.commons.util.LogUtil.warn;
import static com.scottwseo.dvdstore.config.EnvVariables.CONFIG_URL;

public class DVDStoreApplication extends Application<DVDStoreAPIConfiguration> {

    public static void main(String[] args) throws Exception {
        Map<String, String> env = new HashMap<>();

        env.put("SWS_API_CONFIG_URL", "http://localhost:8000/localhost.config.properties");

        EnvUtil.setEnv(env);

        new DVDStoreApplication().run("server", "dvdstore-api/conf/dvdstore-api.yml");
    }

    protected WebsocketBundle websocket = new WebsocketBundle<>();

    @Override
    public void initialize(final Bootstrap<DVDStoreAPIConfiguration> bootstrap) {

        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        if (EnvVariables.check()) {
            ConfigManager.initialize(CONFIG_URL.value(), getProfileName());
        }

        bootstrap.addBundle(new TogglzBundle<>());

        bootstrap.addBundle(new AuthenticationBundle<>("Commons"));

        bootstrap.addBundle(new ViewBundle<>());

        // for assets folder
        bootstrap.addBundle(new AssetsBundle());

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/help",    "/com/scottwseo/commons/help", "index.html",    "help"));

        bootstrap.addBundle(new AssetsBundle("/com/scottwseo/commons/swagger", "/swagger",                    "index.ftl",     "swagger-ui"));

        bootstrap.addBundle(new AssetsBundle("/openapi",                       "/openapi",                    "swagger.json",  "swagger-def"));

        bootstrap.addBundle(new ZipkinBundle<APIConfiguration>(getName()) {
            @Override
            public ZipkinFactory getZipkinFactory(APIConfiguration configuration) {
                return configuration.getZipkinFactory();
            }
        });

        bootstrap.addBundle(websocket);
    }

    private Environment environment;

    @Override
    public void run(final DVDStoreAPIConfiguration configuration,
                    final Environment environment) {
        this.environment = environment;

        java.security.Security.setProperty("networkaddress.cache.ttl", "60");

        Injector injector = Guice.createInjector(new DVDStoreServiceModule(configuration, environment));

        if (EnvVariables.check() && Configs.check() && PostgreSQLDatabase.check()) {

            RxJavaPlugins.getInstance().registerSchedulersHook(new BraveRxJavaSchedulersHook(injector.getInstance(Brave.class)));

            HelpResource helpResource = injector.getInstance(HelpResource.class);
            helpResource.setCfgs(Configs.values());
            helpResource.setEnvs(EnvVariables.values());
            registerResource(helpResource);

            registerResource(injector.getInstance(CategoryResource.class));

            registerResource(injector.getInstance(ProductsResource.class));

            registerResource(injector.getInstance(OrdersResource.class));

            registerResource(injector.getInstance(CustomersResource.class));

            environment.jersey().register(new UnrecognizedPropertyExceptionMapper());

            info("server.startup.completed", "");
        }
        else {
            List<String> errorMessages = ErrorMessageBuilder.errorMessage();

            environment.jersey().register(new StartupCheckListResource(errorMessages));

            environment.healthChecks().register("dummy", new DummyHealthCheck());

            warn("prelaunch.check.failed", errorMessages.toString());
        }

        websocket.addEndpoint(LogEndPoint.class);

    }

    protected String getProfileName() {
        return null;
    }

    private void registerResource(Object o) {
        this.environment.jersey().register(o);
    }

}
