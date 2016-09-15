package com.scottwseo.commons.resources;

import com.scottwseo.commons.auth.User;
import com.scottwseo.commons.help.HelpView;
import com.scottwseo.commons.help.SwaggerView;
import com.scottwseo.commons.help.TailView;
import com.scottwseo.commons.util.Configs;
import io.dropwizard.auth.Auth;
import io.dropwizard.views.View;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
public class HelpResource {

    private HelpView helpView;

    private TailView tailView;

    private SwaggerView swaggerView;

    private String appName;

    private String appVersion;

    public HelpResource(String applicationContextPath, String swaggerloc, String appName, String appVersion) {
        this.appName = appName;
        this.appVersion =  appVersion;
        this.helpView = new HelpView(applicationContextPath);
        this.tailView = new TailView(applicationContextPath);
        this.swaggerView = new SwaggerView(applicationContextPath, swaggerloc);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/help")
    public HelpView help(@Auth User user) {
        return helpView;
    }

    @GET
    @Path("/meta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Map meta() {

        Map<String, Object> meta = new HashMap();

        meta.put("tag", appVersion);
        meta.put("appname", appName);
        List<Map> configs = new ArrayList<>();
        for (Configs config : Configs.values()) {
            Map<String, String> map = new HashMap<>();
            String value = Configs.isMasked(config) ? "******" : config.getString();
            map.put("name", config.name());
            map.put("key", config.key());
            map.put("value", value);
            map.put("required", "" + Configs.isRequired(config));
            configs.add(map);
        }
        meta.put("configs", configs);

        return meta;

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/log")
    public TailView log(@Auth User user) {
        return tailView;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/swagger")
    public View swagger(@Auth User user) {
        return swaggerView;
    }

    @GET
    @Path("/metrics")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response metrics(@Context UriInfo uriInfo) {

        String baseUri = uriInfo.getBaseUri().toString();

        // the internal call should not deal network firewall, so localhost is always safe
        String host = "http://localhost";

        String metrics = get("http://localhost:8081/api/v1/dvdstore/metrics");

        Map json = new HashMap<>();
        json.put("baseUri", baseUri);
        json.put("host", host);
        json.put("metrics", metrics);

        return Response.ok().entity(metrics).build();
    }

    protected String get(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        String responseBody = null;
        try {
            okhttp3.Response response = client.newCall(request).execute();

            responseBody = response.body().string();

        } catch (IOException e) {
        }

        return responseBody;
    }

}