package com.scottwseo.dvdstore.resources;

import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.dvdstore.DVDStoreApplication;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by seos on 10/5/16.
 */
public class CategoryResourceIT {

    private static Logger LOG = LoggerFactory.getLogger(CategoryResourceIT.class);

    private static final String API_URL = "http://localhost:%d/api/v1/dvdstore/categories";

    private static Client client;

    @ClassRule
    public static final DropwizardAppRule<APIConfiguration> RULE =
            new DropwizardAppRule<>(DVDStoreApplication.class, ResourceHelpers.resourceFilePath("test-dvdstore-api.yml"));

    @BeforeClass
    public static void init() {
        client = new JerseyClientBuilder(RULE.getEnvironment()).using(RULE.getConfiguration().getJerseyClientConfiguration()).build("");
    }

    @Test
    public void categories() throws Exception {

        Response response = client.target(String.format(API_URL, RULE.getLocalPort())).request().get();

        int status = response.getStatus();

        if (status != 200) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        assertThat(status, is(200));

        List<String> categories = response.readEntity(List.class);

        assertThat(categories, is(notNullValue()));

    }

}

