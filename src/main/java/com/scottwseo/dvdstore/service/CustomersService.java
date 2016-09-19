package com.scottwseo.dvdstore.service;

import com.scottwseo.dvdstore.api.Customer;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by seos on 9/19/16.
 */
public interface CustomersService {

    Customer getCustomerById(Long customerId, SecurityContext securityContext);

}
