package com.scottwseo.dvdstore.service;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Customer;
import com.scottwseo.dvdstore.jdbi.CustomerMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.core.SecurityContext;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.map;
import static com.scottwseo.commons.util.LogUtil.warn;

/**
 * Created by seos on 9/19/16.
 */
public class CustomerServiceImpl implements CustomersService {

    private DBI dbi;

    @Inject
    public CustomerServiceImpl(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public Customer getCustomerById(Long customerId, SecurityContext securityContext) {
        try (Handle h = dbi.open()) {
            String sql =
                    "SELECT\n" +
                            "    customerid,\n" +
                            "    firstname,\n" +
                            "    lastname,\n" +
                            "    address1,\n" +
                            "    address2,\n" +
                            "    city,\n" +
                            "    state,\n" +
                            "    zip,\n" +
                            "    country,\n" +
                            "    region,\n" +
                            "    email,\n" +
                            "    phone,\n" +
                            "    creditcardtype,\n" +
                            "    creditcard,\n" +
                            "    creditcardexpiration,\n" +
                            "    username,\n" +
                            "    password,\n" +
                            "    age,\n" +
                            "    income,\n" +
                            "    gender\n" +
                            "FROM\n" +
                            "    customers\n" +
                            "WHERE\n" +
                            "    customerid = :customerid ";
            Customer customer = h.createQuery(sql.toString())
                    .bind("customerid", customerId)
                    .map(new CustomerMapper())
                    .first();

            if (customer == null) {
                return new Customer().error(map("customer.findbyid.failed", "", "customerid", customerId, "statusCode", 404));
            }

            return customer;
        }
        catch (Exception e) {
            Map error = warn("customer.update.failed", e.getMessage(), "customerid", customerId, "statusCode", 500);
            return new Customer().error(error);
        }
    }

}
