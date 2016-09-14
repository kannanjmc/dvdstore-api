package com.scottwseo.commons.logging;

import be.tomcools.dropwizard.websocket.WebsocketHandler;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.Server;

public class ServerFactoryWrapper implements ServerFactory {
    private WebsocketHandler handler;
    private ServerFactory serverFactory;

    public ServerFactoryWrapper(ServerFactory serverFactory, WebsocketHandler handler) {
        this.serverFactory = serverFactory;
        this.handler = handler;
    }

    @Override
    public Server build(Environment environment) {
        Server server = serverFactory.build(environment);
        environment.getApplicationContext().setServer(server);
        environment.getAdminContext().setServer(server);
        handler.initialize();
        return server;
    }

    /**
     * Configures the given environment with settings defined in the factory.
     *
     * @param environment the application's environment
     */
    public void configure(Environment environment) {

    }

    public ServerFactory getServerFactory() {
        return this.serverFactory;
    }

}
