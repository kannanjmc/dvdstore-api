package com.scottwseo.commons.rest.resources;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.scottwseo.commons.config.Config;
import com.scottwseo.commons.help.HelpView;
import com.scottwseo.commons.help.OverviewView;
import com.scottwseo.commons.help.TailView;
import com.scottwseo.commons.rest.auth.User;
import io.dropwizard.auth.Auth;
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

    private OverviewView overview;

    private String appName;

    private String applicationContextPath;

    private String appVersion;

    private Config[] envs;

    private Config[] cfgs;

    @Inject
    public void setAppName(@Named("AppName") String appName) {
        this.appName = appName;
    }

    @Inject
    public void setApplicationContextPath(@Named("ApplicationContextPath") String applicationContextPath) {
        this.applicationContextPath = applicationContextPath;
    }

    @Inject
    public void setAppVersion(@Named("AppVersion") String appVersion) {
        this.appVersion = appVersion;
    }

    public HelpResource() {
    }

    public void setEnvs(Config[] envs) {
        this.envs = envs;
    }

    public void setCfgs(Config[] cfgs) {
        this.cfgs = cfgs;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/help")
    public HelpView help(@Auth User user) {
        if (helpView == null) {
            this.helpView = new HelpView(applicationContextPath);
        }
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

        for (Config env : envs) {
            String value = env.masked() ? "******" : env.value();
            populateCfg(configs, env.name(), env.key(), value, env.required(), "environment");
        }

        for (Config config : cfgs) {
            String value = config.masked() ? "******" : config.value();
            populateCfg(configs, config.name(), config.key(), value, config.required(), "dynamic");
        }

        meta.put("configs", configs);

        return meta;

    }

    private void populateCfg(List<Map> configs, String name, String key, String value, boolean required, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("name", name);
        map.put("key", key);
        map.put("value", value);
        map.put("required", "" + required);
        configs.add(map);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/log")
    public TailView log(@Auth User user) {
        if (this.tailView == null) {
            this.tailView = new TailView(applicationContextPath);
        }
        return tailView;
    }

    @GET
    @Path("/metrics")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response metrics(@Context UriInfo uriInfo) {

        String baseUri = uriInfo.getBaseUri().toString();

        // the internal call should not deal network firewall, so localhost is always safe
        String host = "http://localhost";

        String metrics = get("http://localhost:8081" + applicationContextPath + "/metrics");

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

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/overview")
    public OverviewView overview() {
        if (overview == null) {
            this.overview = new OverviewView();
        }
        return overview;
    }


}