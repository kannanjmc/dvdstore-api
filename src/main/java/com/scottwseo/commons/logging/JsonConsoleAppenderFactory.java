package com.scottwseo.commons.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.contrib.jackson.JacksonJsonFormatter;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.logging.AbstractAppenderFactory;
import io.dropwizard.logging.async.AsyncAppenderFactory;
import io.dropwizard.logging.filter.LevelFilterFactory;
import io.dropwizard.logging.layout.LayoutFactory;

@JsonTypeName("json")
public class JsonConsoleAppenderFactory extends AbstractAppenderFactory {

    private String target;

    @JsonProperty
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }

    public JsonConsoleAppenderFactory() {
    }

    @Override
    public Appender build(LoggerContext context, String applicationName, LayoutFactory layoutFactory, LevelFilterFactory levelFilterFactory, AsyncAppenderFactory asyncAppenderFactory) {
        ConsoleAppender appender = new ConsoleAppender();
        appender.setName("json-console-appender");
        appender.setContext(context);
        appender.setTarget("System.out");

        LayoutWrappingEncoder layoutEncoder = new LayoutWrappingEncoder();
        JacksonJsonFormatter jsonFormatter = new JacksonJsonFormatter();
        jsonFormatter.setPrettyPrint(false);

        JsonLayout jsonLayout = new CustomJsonLayout();
        jsonLayout.setIncludeLoggerName(false);
        jsonLayout.setIncludeThreadName(false);
        jsonLayout.setIncludeContextName(false);
        jsonLayout.setIncludeTimestamp(true);
        jsonLayout.setJsonFormatter(jsonFormatter);
        jsonLayout.setTimestampFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        jsonLayout.setTimestampFormatTimezoneId("UTC");
        jsonLayout.setAppendLineSeparator(true);

        layoutEncoder.setLayout(jsonLayout);

        appender.setEncoder(layoutEncoder);
        appender.addFilter(levelFilterFactory.build(threshold));
        appender.start();
        return this.wrapAsync(appender, asyncAppenderFactory);
    }

}
