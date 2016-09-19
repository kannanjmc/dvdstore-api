package com.scottwseo.dvdstore.guice;

import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.commons.guice.ServiceModule;
import com.scottwseo.dvdstore.service.*;
import io.dropwizard.setup.Environment;

public class DVDStoreServiceModule extends ServiceModule {

    public DVDStoreServiceModule(APIConfiguration configuration, Environment environment) {
        super(configuration, environment);
    }

    @Override
    protected void configure() {
        bind(CategoryService.class).to(CategoryServiceImpl.class);
        bind(ProductsService.class).to(ProductsServiceImpl.class);
        bind(OrdersService.class).to(OrdersServiceImpl.class);
        bind(CustomersService.class).to(CustomerServiceImpl.class);
    }

}
