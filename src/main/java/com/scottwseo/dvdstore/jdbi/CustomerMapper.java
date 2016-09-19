package com.scottwseo.dvdstore.jdbi;

import com.scottwseo.dvdstore.api.Customer;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.warn;

public class CustomerMapper implements ResultSetMapper<Customer> {

    public Customer map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        Customer customer = new Customer();

        try {
            customer
                    .customerid(r.getString("customerid"))
                    .firstname(r.getString("firstname"))
                    .lastname(r.getString("lastname"))
                    .address1(r.getString("address1"))
                    .address2(r.getString("address2"))
                    .city(r.getString("city"))
                    .state(r.getString("state"))
                    .zip(r.getInt("zip"))
                    .country(r.getString("country"))
                    .region(r.getInt("region"))
                    .email(r.getString("email"))
                    .phone(r.getString("phone"))
                    .creditcardtype(r.getInt("creditcardtype"))
                    .creditcard(r.getString("creditcard"))
                    .creditcardexpiration(r.getString("creditcardexpiration"))
                    .username(r.getString("username"))
                    .password(r.getString("password"))
                    .age(r.getInt("age"))
                    .income(r.getInt("income"))
                    .gender(r.getString("gender"));
        } catch (Exception e) {
            Map error = warn("rset to customer mapping failed", e.getMessage(), "ctx", ctx);
            customer.error(error);
        }

        return customer;
    }

}