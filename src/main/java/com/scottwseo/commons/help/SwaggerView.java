package com.scottwseo.commons.help;

import io.dropwizard.views.View;

/**
 * Created by seos on 4/14/15.
 */
public class SwaggerView extends View {

    private String applicationContextPath;

    private String swaggerloc;

    public SwaggerView(String applicationContextPath, String swaggerloc) {
        super("/com/scottwseo/commons/swagger/index.ftl");
        this.applicationContextPath = applicationContextPath;
        this.swaggerloc = swaggerloc;
    }

    public String getBase() {
        return applicationContextPath;
    }

    // swagger folder path under /resources e.g. /resources/my-swagger
    public String getSwaggerloc() {
        return swaggerloc;
    }

}
