package com.scottwseo.dvdstore.guice;

import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.commons.guice.ServiceModule;
import com.scottwseo.dvdstore.service.CategoryService;
import com.scottwseo.dvdstore.service.CategoryServiceImpl;
import io.dropwizard.setup.Environment;

public class DVDStoreServiceModule extends ServiceModule {

    public DVDStoreServiceModule(APIConfiguration configuration, Environment environment) {
        super(configuration, environment);
    }

    @Override
    protected void configure() {
        bind(CategoryService.class).to(CategoryServiceImpl.class);
    }

}
