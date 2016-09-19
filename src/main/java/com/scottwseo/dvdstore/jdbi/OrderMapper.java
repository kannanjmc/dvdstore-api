package com.scottwseo.dvdstore.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.warn;

public class OrderMapper implements ResultSetMapper<OrderAndOrderLine> {

    public OrderAndOrderLine map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        OrderAndOrderLine orderAndOrderLine = new OrderAndOrderLine();

        try {
            orderAndOrderLine.orderId(r.getLong("orderid"))
                 .orderDate(r.getString("orderdate"))
                 .customerId(r.getLong("customerid"))
                 .netAmount(r.getBigDecimal("netamount"))
                 .tax(r.getBigDecimal("tax"))
                 .totalAmount(r.getBigDecimal("totalamount"))
                 .orderLineId(r.getLong("orderlineid"))
                 .productId(r.getLong("prod_id"))
                 .quantity(r.getInt("quantity"));
        }
        catch (Exception e) {
            Map error = warn("rset to order mapping failed", e.getMessage(), "ctx", ctx);
            orderAndOrderLine.error(error);
        }

        return orderAndOrderLine;
    }

}