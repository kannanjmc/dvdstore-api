package com.scottwseo.commons.auth;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by seos on 9/8/16.
 */
public class AuthenticationBundle<T extends Configuration>  implements ConfiguredBundle<T> {

    private String realm;

    public AuthenticationBundle(String realm) {
        this.realm = realm;
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new SimpleAuthenticator())
                        .setAuthorizer(new SimpleAuthorizer())
                        .setRealm(realm)
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

}
