package com.scottwseo.commons.togglz;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.togglz.console.TogglzConsoleServlet;
import org.togglz.servlet.TogglzFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by seos on 9/8/16.
 */
public class TogglzBundle<T extends Configuration>  implements ConfiguredBundle<T> {

    public TogglzBundle() {
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        TogglzFilter togglzFilter =  new TogglzFilter();
        environment.servlets().addFilter("TogglzFilter", togglzFilter)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        environment.servlets().addServlet("TogglzConsoleServlet", TogglzConsoleServlet.class)
                .addMapping("/togglz/*");
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

}
