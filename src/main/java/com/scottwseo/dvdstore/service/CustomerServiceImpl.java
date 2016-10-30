package com.scottwseo.dvdstore.service;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Customer;
import com.scottwseo.dvdstore.jdbi.CustomerMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.LongColumnMapper;

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

    public Customer createCustomer(Customer customer, SecurityContext securityContext) {
        Customer existingCustomer = getCustomerByUsername(customer.getUsername(), securityContext);

        if (existingCustomer != null && customer.getUsername().equals(existingCustomer.getUsername())) {
            return customer.error(map("customer.create.failed", "username exist", "customer", customer, "statusCode", 400));
        }

        try (Handle h = dbi.open()) {
            String sql =
                    "INSERT\n" +
                            "INTO\n" +
                            "    public.customers\n" +
                            "    (\n" +
                            "        firstname,\n" +
                            "        lastname,\n" +
                            "        address1,\n" +
                            "        address2,\n" +
                            "        city,\n" +
                            "        state,\n" +
                            "        zip,\n" +
                            "        country,\n" +
                            "        region,\n" +
                            "        email,\n" +
                            "        phone,\n" +
                            "        creditcardtype,\n" +
                            "        creditcard,\n" +
                            "        creditcardexpiration,\n" +
                            "        username,\n" +
                            "        password,\n" +
                            "        age,\n" +
                            "        income,\n" +
                            "        gender\n" +
                            "    )\n" +
                            "    VALUES\n" +
                            "    (\n" +
                            "        :firstname,\n" +
                            "        :lastname,\n" +
                            "        :address1,\n" +
                            "        :address2,\n" +
                            "        :city,\n" +
                            "        :state,\n" +
                            "        :zip,\n" +
                            "        :country,\n" +
                            "        :region,\n" +
                            "        :email,\n" +
                            "        :phone,\n" +
                            "        :creditcardtype,\n" +
                            "        :creditcard,\n" +
                            "        :creditcardexpiration,\n" +
                            "        :username,\n" +
                            "        :password,\n" +
                            "        :age,\n" +
                            "        :income,\n" +
                            "        :gender\n" +
                            "    )";
            long customerId = h.createStatement(sql.toString())
                    .bind("firstname", customer.getFirstname())
                    .bind("lastname", customer.getLastname())
                    .bind("address1", customer.getAddress1())
                    .bind("address2", customer.getAddress2())
                    .bind("city", customer.getCity())
                    .bind("state", customer.getState())
                    .bind("zip", customer.getZip())
                    .bind("country", customer.getCountry())
                    .bind("region", customer.getRegion())
                    .bind("email", customer.getEmail())
                    .bind("phone", customer.getPhone())
                    .bind("creditcardtype", customer.getCreditcardtype())
                    .bind("creditcard", customer.getCreditcard())
                    .bind("creditcardexpiration", customer.getCreditcardexpiration())
                    .bind("username", customer.getUsername())
                    .bind("password", customer.getPassword())
                    .bind("age", customer.getAge())
                    .bind("income", customer.getIncome())
                    .bind("gender", customer.getGender().toString())
                    .executeAndReturnGeneratedKeys(LongColumnMapper.PRIMITIVE).first();

            return customer.customerid(customerId);
        }
        catch (Exception e) {
            Map error = warn("customer.create.failed", e.getMessage(), "customer", customer, "statusCode", 500);
            return customer.error(error);
        }
    }

    @Override
    public Customer getCustomerByUsername(String username, SecurityContext securityContext) {
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
                            "    username = :username ";
            Customer customer = h.createQuery(sql.toString())
                    .bind("username", username)
                    .map(new CustomerMapper())
                    .first();

            if (customer == null) {
                return new Customer().error(map("customer.findbyid.failed", "", "username", username, "statusCode", 404));
            }

            return customer;
        }
        catch (Exception e) {
            Map error = warn("customer.update.failed", e.getMessage(), "username", username, "statusCode", 500);
            return new Customer().error(error);
        }
    }

    @Override
    public Map deleteCustomerByUsername(String username, SecurityContext securityContext) {
        try (Handle h = dbi.open()) {
            String sql =
                    "DELETE\n" +
                        "FROM\n" +
                        "    customers\n" +
                        "WHERE\n" +
                        "    username = :username ";
            int rows = h.createStatement(sql.toString())
                    .bind("username", username)
                    .execute();

            if (rows == 1) {
                return null;
            }
            else {
                return map("customer.delete.failed", "delete affected more than one row", "username", username, "statusCode", 500);
            }
        }
        catch (Exception e) {
            Map error = warn("customer.delete.failed", e.getMessage(), "username", username, "statusCode", 500);
            return new Customer().error(error);
        }
    }

    @Override
    public Customer updateCustomerByUsername(Customer customer, String username, SecurityContext securityContext) {
        Customer existingCustomer = getCustomerByUsername(username, securityContext);

        if (existingCustomer.error() != null) {
            return customer.error(existingCustomer.error());
        }

        try (Handle h = dbi.open()) {
            String sql =
                    "UPDATE\n" +
                            "    customers\n" +
                            "SET\n" +
                            "    firstname = :firstname,\n" +
                            "    lastname = :lastname,\n" +
                            "    address1 = :address1,\n" +
                            "    address2 = :address2,\n" +
                            "    city = :city,\n" +
                            "    state = :state,\n" +
                            "    zip = :zip,\n" +
                            "    country = :country,\n" +
                            "    region = :region,\n" +
                            "    email = :email,\n" +
                            "    phone = :phone,\n" +
                            "    creditcardtype = :creditcardtype,\n" +
                            "    creditcard = :creditcard,\n" +
                            "    creditcardexpiration = :creditcardexpiration,\n" +
                            "    password = :password,\n" +
                            "    age = :age,\n" +
                            "    income = :income,\n" +
                            "    gender = :gender\n" +
                            "WHERE\n" +
                            "    username = :username";
            long rowsUpdated = h.createStatement(sql.toString())
                    .bind("firstname", customer.getFirstname())
                    .bind("lastname", customer.getLastname())
                    .bind("address1", customer.getAddress1())
                    .bind("address2", customer.getAddress2())
                    .bind("city", customer.getCity())
                    .bind("state", customer.getState())
                    .bind("zip", customer.getZip())
                    .bind("country", customer.getCountry())
                    .bind("region", customer.getRegion())
                    .bind("email", customer.getEmail())
                    .bind("phone", customer.getPhone())
                    .bind("creditcardtype", customer.getCreditcardtype())
                    .bind("creditcard", customer.getCreditcard())
                    .bind("creditcardexpiration", customer.getCreditcardexpiration())
                    .bind("password", customer.getPassword())
                    .bind("age", customer.getAge())
                    .bind("income", customer.getIncome())
                    .bind("gender", customer.getGender().toString())
                    .bind("username", username)
                    .execute();

            if (rowsUpdated == 1) {
                return customer;
            }
            else {
                Map error = warn("customer.update.unexepected", "updated affected multiple row", "customer", customer, "statusCode", 500);
                return customer.error(error);
            }
        }
        catch (Exception e) {
            Map error = warn("customer.update.failed", e.getMessage(), "customer", customer, "statusCode", 500);
            return customer.error(error);
        }
    }
}
