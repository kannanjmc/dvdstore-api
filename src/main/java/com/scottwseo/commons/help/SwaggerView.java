package com.scottwseo.commons.help;

import io.dropwizard.views.View;

/**
 * Created by seos on 4/14/15.
 */
public class SwaggerView extends View {

    private String applicationContextPath;

    public SwaggerView(String applicationContextPath) {
        super("/com/scottwseo/commons/swagger/index.ftl");
        this.applicationContextPath = applicationContextPath;
    }

    public String getBase() {
        return applicationContextPath;
    }

}
