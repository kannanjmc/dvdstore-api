package com.scottwseo.commons.app;

import com.github.kristofa.brave.Brave;
import com.google.inject.Inject;
import com.scottwseo.commons.exception.UnrecognizedPropertyExceptionMapper;
import com.scottwseo.commons.resources.HelpResource;
import com.scottwseo.dvdstore.resources.CategoryResource;
import com.scottwseo.dvdstore.resources.CustomersResource;
import com.scottwseo.dvdstore.resources.OrdersResource;
import com.scottwseo.dvdstore.resources.ProductsResource;
import com.smoketurner.dropwizard.zipkin.rx.BraveRxJavaSchedulersHook;
import io.dropwizard.setup.Environment;
import rx.plugins.RxJavaPlugins;

import static com.scottwseo.commons.util.LogUtil.info;

/**
 * Created by seos on 11/6/16.
 */
public class LaunchSuccessHandler implements LaunchHandler {

    private Environment environment;

    @Inject
    private HelpResource helpResource;

    @Inject
    private CategoryResource categoryResource;

    @Inject
    private ProductsResource productsResource;

    @Inject
    private OrdersResource ordersResource;

    @Inject
    private CustomersResource customersResource;

    @Inject
    private Brave brave;

    public void run(Environment environment) {
        this.environment = environment;

        RxJavaPlugins.getInstance()
                .registerSchedulersHook(new BraveRxJavaSchedulersHook(brave));

        registerResource(helpResource);

        registerResource(categoryResource);

        registerResource(productsResource);

        registerResource(ordersResource);

        registerResource(customersResource);

        environment.jersey().register(new UnrecognizedPropertyExceptionMapper());

        info("server.startup.completed", "");

    }

    private void registerResource(Object o) {
        this.environment.jersey().register(o);
    }

}
