package com.scottwseo.dvdstore.service;

import com.scottwseo.dvdstore.api.Customer;

import javax.ws.rs.core.SecurityContext;
import java.util.Map;

/**
 * Created by seos on 9/19/16.
 */
public interface CustomersService {

    Customer createCustomer(Customer customer, SecurityContext securityContext);

    Map deleteCustomerByUsername(String username, SecurityContext securityContext);

    Customer getCustomerByUsername(String username, SecurityContext securityContext);

    Customer updateCustomerByUsername(Customer customer, String username, SecurityContext securityContext);

}
