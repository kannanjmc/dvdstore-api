package com.scottwseo.dvdstore.jdbi;

import com.scottwseo.dvdstore.api.Order;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.warn;

public class OrderMapper implements ResultSetMapper<Order> {

    public Order map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        Order order = new Order();

        try {
            order.orderId(r.getLong("orderid"))
                 .orderDate(r.getString("orderdate"))
                 .customerId(r.getLong("customerid"))
                 .netAmount(r.getBigDecimal("netamount"))
                 .tax(r.getBigDecimal("tax"))
                 .totalAmount(r.getBigDecimal("totalamount"));
        }
        catch (Exception e) {
            Map error = warn("rset to order mapping failed", e.getMessage(), "ctx", ctx);
            order.error(error);
        }

        return order;
    }

}