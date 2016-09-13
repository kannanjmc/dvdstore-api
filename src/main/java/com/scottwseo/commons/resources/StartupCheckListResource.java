package com.scottwseo.commons.resources;

import com.scottwseo.commons.util.Configs;
import com.scottwseo.commons.util.EnvVariables;
import com.scottwseo.commons.util.PostgreSQLDatabase;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/{default: .*}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StartupCheckListResource {

    @GET
    public Map<String, Object> index() {
        Map<String, Object> message = new HashMap<>();

        message.put("code", "500");
        message.put("message", "check failures");

        List<String> s = new ArrayList<>();

        if (!EnvVariables.check()) {
            s.addAll(EnvVariables.missing());
        }

        if (!Configs.check()) {
            for (Configs config : Configs.values()) {
                if (Configs.isRequired(config) && !config.isProvided()) {
                    s.add("config [" + config.key() + "] missing");
                }
            }
        }
        else if (!PostgreSQLDatabase.check()) {
            s.add("PostgreSQL connection failure. URL: " + PostgreSQLDatabase.url());
        }

        message.put("failures", s);

        return message;

    }

}