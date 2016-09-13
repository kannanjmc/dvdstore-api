package com.scottwseo.commons.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.Appender;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.logging.AbstractAppenderFactory;
import io.dropwizard.logging.async.AsyncAppenderFactory;
import io.dropwizard.logging.filter.LevelFilterFactory;
import io.dropwizard.logging.layout.LayoutFactory;

@JsonTypeName("websocket")
public class WebSocketAppenderFactory extends AbstractAppenderFactory {

    private String target;

    @JsonProperty
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }

    public WebSocketAppenderFactory() {
    }

    @Override
    public Appender build(LoggerContext context, String applicationName, LayoutFactory layoutFactory, LevelFilterFactory levelFilterFactory, AsyncAppenderFactory asyncAppenderFactory) {
        WebSocketAppender appender = new WebSocketAppender();
        appender.setName("websocket-appender");
        appender.setContext(context);
        appender.addFilter(levelFilterFactory.build(threshold));
        appender.start();
        return this.wrapAsync(appender, asyncAppenderFactory);
    }

}
