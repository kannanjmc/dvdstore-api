package com.scottwseo.commons.help;

import io.dropwizard.views.View;

/**
 * Created by seos on 4/14/15.
 */
public class HelpView extends View {

    private String applicationContextPath;

    public HelpView(String applicationContextPath) {
        super("help.ftl");
        this.applicationContextPath = applicationContextPath;
    }

    public String getBase() {
        return applicationContextPath;
    }

}
