package com.scottwseo.commons.app;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.scottwseo.commons.util.Configs;
import com.scottwseo.commons.util.EnvVariables;
import com.scottwseo.commons.util.PostgreSQLDatabase;

/**
 * Created by seos on 11/6/16.
 */
public class LaunchHandlerProvider implements Provider<LaunchHandler> {

    private LaunchSuccessHandler launchSuccessHandler;

    private LaunchFailureHandler launchFailureHandler;

    @Inject
    public LaunchHandlerProvider(LaunchSuccessHandler launchSuccessHandler, LaunchFailureHandler launchFailureHandler) {
        this.launchSuccessHandler = launchSuccessHandler;
        this.launchFailureHandler = launchFailureHandler;
    }

    public LaunchHandler get() {
        if (EnvVariables.check() && Configs.check() && PostgreSQLDatabase.check()) {
            return launchSuccessHandler;
        }
        else {
            return launchFailureHandler;
        }
    }

}
