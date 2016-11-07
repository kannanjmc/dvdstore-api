package com.scottwseo.dvdstore;

import com.scottwseo.commons.rest.app.APIConfiguration;
import com.scottwseo.dvdstore.resources.CategoryResourceIT;
import com.scottwseo.dvdstore.resources.CustomersResourceIT;
import com.scottwseo.dvdstore.resources.OrdersResourceIT;
import com.scottwseo.dvdstore.resources.ProductsResourceIT;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.ws.rs.client.Client;

/**
 * Created by seos on 10/31/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CategoryResourceIT.class,
        CustomersResourceIT.class,
        ProductsResourceIT.class,
        OrdersResourceIT.class})
public class TestSuiteIT {

    @ClassRule
    public static final DropwizardAppRule<APIConfiguration> DROPWIZARD =
            new DropwizardAppRule<>(DVDStoreApplication.class, ResourceHelpers.resourceFilePath("test-dvdstore-api.yml"));

    public static Client CLIENT = null;

    @BeforeClass
    public static void init() {
        CLIENT = new JerseyClientBuilder(DROPWIZARD.getEnvironment()).using(DROPWIZARD.getConfiguration().getJerseyClientConfiguration()).build("test-client");
    }

}
