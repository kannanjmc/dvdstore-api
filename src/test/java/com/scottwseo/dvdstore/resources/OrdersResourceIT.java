package com.scottwseo.dvdstore.resources;

import com.scottwseo.commons.rest.app.APIConfiguration;
import com.scottwseo.dvdstore.DVDStoreAPIConfiguration;
import com.scottwseo.dvdstore.TestSuiteIT;
import com.scottwseo.dvdstore.api.Order;
import com.scottwseo.dvdstore.api.OrderLine;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public static final DropwizardAppRule<DVDStoreAPIConfiguration> RULE = TestSuiteIT.DROPWIZARD;

    private static Client client = TestSuiteIT.CLIENT;

    @Test
    public void addOrder() throws Exception {
        Order order = createOrder();

        assertThat(order, is(notNullValue()));

        assertThat(order.getCustomerId(), is(1L));
    }

    @Test
    public void deleteOrder() throws Exception {
        Order order = createOrder();

        Response response = client.target(String.format(API_URL + "/" + order.getOrderId(), RULE.getLocalPort())).request().delete();

        int status = response.getStatus();

        if (status != 204) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        assertThat(status, is(204));
    }

    @Test
    public void getOrderById() throws Exception {
        Order order = createOrder();

        Response response = client.target(String.format(API_URL + "/" + order.getOrderId(), RULE.getLocalPort())).request().get();

        int status = response.getStatus();

        if (status != 200) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        Order orderReceived = response.readEntity(Order.class);

        assertThat(status, is(200));

        assertThat(orderReceived, is(notNullValue()));

        assertThat(order.getOrderId(), is(orderReceived.getOrderId()));
    }

    private Order createOrder() throws Exception {
        Order order = new Order();
        order.customerId(1L);
        order.setNetAmount(new BigDecimal(1000.00));
        order.setTax(new BigDecimal(86.5));
        order.setTotalAmount(new BigDecimal(1086.5));
        order.setOrderDate("11/1/2016");

        List<OrderLine> orderLines = new ArrayList<>();
        for (long i = 0 ; i < 5 ; i++) {
            OrderLine orderLine = new OrderLine();
            orderLine.setOrderDate("11/1/2016");
            orderLine.setProductId(1L);
            orderLine.setQuantity(1);
            orderLines.add(orderLine);
        }
        order.setOrderlines(orderLines);

        Response response = client.target(String.format(API_URL, RULE.getLocalPort())).request().post(Entity.json(order));

        int status = response.getStatus();

        if (status != 200) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        assertThat(status, is(200));

        Order orderReceived = response.readEntity(Order.class);

        return orderReceived;
    }
}

