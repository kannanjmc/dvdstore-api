package com.scottwseo.dvdstore.resources;

import com.scottwseo.commons.rest.app.APIConfiguration;
import com.scottwseo.dvdstore.DVDStoreAPIConfiguration;
import com.scottwseo.dvdstore.TestSuiteIT;
import com.scottwseo.dvdstore.api.Customer;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by seos on 10/5/16.
 */
public class CustomersResourceIT {

    private static Logger LOG = LoggerFactory.getLogger(CustomersResourceIT.class);

    private static final String API_URL = "http://localhost:%d/api/v1/dvdstore/user";

    @ClassRule
    public static final DropwizardAppRule<DVDStoreAPIConfiguration> RULE = TestSuiteIT.DROPWIZARD;

    private static Client client = TestSuiteIT.CLIENT;

    public Customer createCustomer() throws Exception {

        Customer customer = new Customer();
        customer.address1("123 Acme blvd");
        customer.address2("1FL");
        customer.city("New York");
        customer.state("NY");
        customer.zip(11111);
        customer.setUsername("user" + System.currentTimeMillis());
        customer.setAge(20);
        customer.setCountry("USA");
        customer.setCreditcard("1234567812345678");
        customer.setCreditcardexpiration("112016");
        customer.setEmail("user@acme.com");
        customer.setFirstname("Tom");
        customer.setLastname("Richardson");
        customer.setRegion(1);
        customer.setPhone("8881234567");
        customer.setIncome(100000);
        customer.setGender(Customer.GenderEnum.M);
        customer.setPassword("password");

        Response response = client.target(String.format(API_URL, RULE.getLocalPort())).request().post(Entity.json(customer));

        int status = response.getStatus();

        if (status != 200) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        assertThat(status, is(200));

        Customer customerCreated = response.readEntity(Customer.class);

        assertThat(customerCreated, is(notNullValue()));

        assertThat(customerCreated.getCustomerid(), is(notNullValue()));

        return customerCreated;

    }

    @Test
    public void getCustomerByUsername() throws Exception {
        Customer customer = createCustomer();

        Response response = client.target(String.format(API_URL + "/" + customer.getUsername(), RULE.getLocalPort())).request().get();

        int status = response.getStatus();

        if (status != 200) {
            String responseBody = response.readEntity(String.class);
            LOG.warn(responseBody);
        }

        assertThat(status, is(200));

        Customer customerRetrieved = response.readEntity(Customer.class);

        assertThat(customerRetrieved, is(notNullValue()));

        assertThat(customerRetrieved.getAge(), equalTo(customer.getAge()));

    }

    @Test
    public void deleteCustomerByUsername() throws Exception {
        Customer customer = createCustomer();

        Response response = client.target(String.format(API_URL + "/" + customer.getUsername(), RULE.getLocalPort())).request().delete();

        int status = response.getStatus();

        assertThat(status, is(204));

        response = client.target(String.format(API_URL + "/" + customer.getUsername(), RULE.getLocalPort())).request().get();

        status = response.getStatus();

        assertThat(status, is(404));
    }

    @Test
    public void updateCustomerByUsername() throws Exception {
        Customer customer = createCustomer();

        customer.setAge(30);

        Response response = client.target(String.format(API_URL + "/" + customer.getUsername(), RULE.getLocalPort())).request().put(Entity.json(customer));

        int status = response.getStatus();

        assertThat(status, is(204));

        response = client.target(String.format(API_URL + "/" + customer.getUsername(), RULE.getLocalPort())).request().get();

        status = response.getStatus();

        assertThat(status, is(200));

        Customer customerUpdated = response.readEntity(Customer.class);

        assertThat(customerUpdated.getAge(), equalTo(30));
    }

    @Test
    public void updateCustomerByUsernameError() throws Exception {

        Customer customer = createCustomer();

        customer.setPhone("0000000000000000000000000000000000000000000000000000000000000000000000");

        Response response = client.target(String.format(API_URL + "/" + customer.getUsername(), RULE.getLocalPort())).request().put(Entity.json(customer));

        int status = response.getStatus();

        assertThat(status, is(500));

        Map error = response.readEntity(Map.class);

        assertThat(error.get("event"), is("customer.update.failed"));
    }

    @Test
    public void updateCustomerByUsernameUserNotFoundError() throws Exception {
        Customer customer = createCustomer();

        customer.setPhone("0000000000000000000000000000000000000000000000000000000000000000000000");

        Response response = client.target(String.format(API_URL + "/foo" + System.currentTimeMillis(), RULE.getLocalPort())).request().put(Entity.json(customer));

        int status = response.getStatus();

        assertThat(status, is(404));

        Map error = response.readEntity(Map.class);

        assertThat(error.get("event"), is("customer.findbyid.failed"));
    }


    @Test
    public void loginCustomer() throws Exception {
        Customer customer = createCustomer();

        Response response = client.target(String.format(API_URL + "/login?username=" + customer.getUsername() + "&password=" + customer.getPassword(), RULE.getLocalPort())).request().get();

        int status = response.getStatus();

        assertThat(status, is(200));
    }

    @Test
    public void logoutCustomer() throws Exception {
        Response response = client.target(String.format(API_URL + "/logout", RULE.getLocalPort())).request().get();

        int status = response.getStatus();

        assertThat(status, is(200));
    }

    @Test
    public void createCustomerexistingCustomer() throws Exception {
        Customer customer = createCustomer();

        customer.setCustomerid(null);

        Response response = client.target(String.format(API_URL, RULE.getLocalPort())).request().post(Entity.json(customer));

        int status = response.getStatus();

        assertThat(status, is(400));

        Map error = response.readEntity(Map.class);

        assertThat(error.get("event"), is("customer.create.failed"));

        assertThat(error.get("description"), is("username exist"));

    }

    @Test
    public void createCustomerError() throws Exception {
        Customer customer = createCustomer();

        customer.setCustomerid(null);

        customer.setPhone("0000000000000000000000000000000000000000000000000000");

        customer.setUsername("user" + System.currentTimeMillis());

        Response response = client.target(String.format(API_URL, RULE.getLocalPort())).request().post(Entity.json(customer));

        int status = response.getStatus();

        assertThat(status, is(500));

        Map error = response.readEntity(Map.class);

        assertThat(error.get("event"), is("customer.create.failed"));

        assertThat(error.get("description"), is(notNullValue()));

    }

}

