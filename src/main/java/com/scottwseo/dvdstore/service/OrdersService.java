package com.scottwseo.dvdstore.service;

import com.scottwseo.dvdstore.api.Order;

import javax.ws.rs.core.SecurityContext;
import java.util.Map;

/**
 * Created by seos on 9/19/16.
 */
public interface OrdersService {

    Order addOrder(Order order, SecurityContext securityContext);

    Map deleteOrder(Long orderId, String apiKey, SecurityContext securityContext);

    Order getOrderById(Long orderId, SecurityContext securityContext);

}
