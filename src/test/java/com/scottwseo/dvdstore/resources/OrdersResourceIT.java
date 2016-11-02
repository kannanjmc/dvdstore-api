package com.scottwseo.dvdstore.resources;

import com.scottwseo.commons.app.APIConfiguration;
import com.scottwseo.dvdstore.TestSuiteIT;
import com.scottwseo.dvdstore.api.Order;
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
public class OrdersResourceIT {

    private static Logger LOG = LoggerFactory.getLogger(OrdersResourceIT.class);

    private static final String API_URL = "http://localhost:%d/api/v1/dvdstore/orders";

    @ClassRule
    public static final DropwizardAppRule<APIConfiguration> RULE = TestSuiteIT.DROPWIZARD;

    private static Client client = TestSuiteIT.CLIENT;

    @Test
    public void addOrder() throws Exception {
        Order order = new Order();
        order.customerId(1L);
        order.setNetAmount(new BigDecimal(1000.00));
        order.setTax(new BigDecimal(86.5));
        order.setTotalAmount(new BigDecimal(1086.5));
        order.setOrderDate("11/1/2016");

        Response response = client.target(String.format(API_URL, RULE.getLocalPort())).request().post(Entity.json(order));

        int status = response.getStatus();

        if (status != 200) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        assertThat(status, is(200));

        Order orderReceived = response.readEntity(Order.class);

        assertThat(orderReceived, is(notNullValue()));

        assertThat(orderReceived.getCustomerId(), is(1L));
    }

    @Test
    public void deleteOrder() throws Exception {

    }

}

