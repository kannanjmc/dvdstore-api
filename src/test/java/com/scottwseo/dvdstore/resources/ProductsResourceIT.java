package com.scottwseo.dvdstore.resources;

import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.dvdstore.DVDStoreApplication;
import com.scottwseo.dvdstore.api.ProductCreate;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by seos on 10/5/16.
 */
public class ProductsResourceIT {

    private static Logger LOG = LoggerFactory.getLogger(ProductsResourceIT.class);

    private static final String API_URL = "http://localhost:%d/api/v1/dvdstore/products";

    @ClassRule
    public static final DropwizardAppRule<APIConfiguration> RULE =
            new DropwizardAppRule<>(DVDStoreApplication.class, ResourceHelpers.resourceFilePath("test-dvdstore-api.yml"));

    @Test
    public void addProduct() throws Exception {

        Client client = new JerseyClientBuilder(RULE.getEnvironment()).using(RULE.getConfiguration().getJerseyClientConfiguration()).build("");

        ProductCreate product = new ProductCreate();
        product.actor("Scott");
        product.category(1L);
        product.title("Scott Goes to Hollywood");
        product.commonProdId(1L);
        product.price(new BigDecimal(10.00));

        Response response = client.target(String.format(API_URL, RULE.getLocalPort())).request().post(Entity.json(product));


        int status = response.getStatus();

        if (status != 200) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        assertThat(status, is(200));

        ProductCreate productReceived = response.readEntity(ProductCreate.class);

        assertThat(productReceived, is(notNullValue()));

    }

}

