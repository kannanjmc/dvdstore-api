package com.scottwseo.dvdstore.service;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Order;
import com.scottwseo.dvdstore.api.Products;
import com.scottwseo.dvdstore.jdbi.OrderMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.LongColumnMapper;

import javax.ws.rs.core.SecurityContext;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.map;
import static com.scottwseo.commons.util.LogUtil.warn;

public class OrdersServiceImpl implements OrdersService {

    private DBI dbi;

    @Inject
    public OrdersServiceImpl(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public Order addOrder(Order order, SecurityContext securityContext) {

        try (Handle h = dbi.open()) {
            String sql = "INSERT\n" +
                    "INTO\n" +
                    "    orders\n" +
                    "    (\n" +
                    "        orderdate,\n" +
                    "        customerid,\n" +
                    "        netamount,\n" +
                    "        tax,\n" +
                    "        totalamount\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (\n" +
                    "        :orderdate,\n" +
                    "        :customerid,\n" +
                    "        :netamount,\n" +
                    "        :tax,\n" +
                    "        :totalamount\n" +
                    "    )";
            long orderId = h.createStatement(sql.toString())
            .bind("orderdate", order.getOrderDate())
            .bind("customerid", order.getCustomerId())
            .bind("netamount", order.getNetAmount())
            .bind("tax", order.getTax())
            .bind("totalamount", order.getTotalAmount())
            .executeAndReturnGeneratedKeys(LongColumnMapper.PRIMITIVE).first();

            return order.orderId(orderId);
        }
        catch (Exception e) {
            Map error = warn("ordere.addorder.failed", e.getMessage(), "order", order);
            return order.error(error);
        }

    }

    @Override
    public Map deleteOrder(Long orderId, String apiKey, SecurityContext securityContext) {
        Order existingOrder = getOrderById(orderId, securityContext);

        if (existingOrder.error() != null) {
            return existingOrder.error();
        }

        try (Handle h = dbi.open()) {
            String sql =
                    "DELETE\n" +
                            "FROM\n" +
                            "    orders\n" +
                            "WHERE\n" +
                            "    orderid = :orderid";
            int rows = h.createStatement(sql.toString())
                    .bind("orderid", orderId)
                    .execute();

            if (rows == 1) {
                return null;
            }
            else {
                return map("order.delete.failed", "delete affected more than one row", "orderid", orderId, "statusCode", 500);
            }
        }
        catch (Exception e) {
            Map error = warn("order.delete.failed", e.getMessage(), "orderid", orderId, "statusCode", 500);
            return new Products().error(error);
        }
    }

    @Override
    public Order getOrderById(Long orderId, SecurityContext securityContext) {
        try (Handle h = dbi.open()) {
            String sql =
                    "SELECT\n" +
                            "    orderid,\n" +
                            "    orderdate,\n" +
                            "    customerid,\n" +
                            "    netamount,\n" +
                            "    tax,\n" +
                            "    totalamount\n" +
                            "FROM\n" +
                            "    orders\n" +
                            "WHERE\n" +
                            "    orderid = :orderid";
            Order order = h.createQuery(sql.toString())
                    .bind("orderid", orderId)
                    .map(new OrderMapper())
                    .first();

            if (order == null) {
                return new Order().error(map("order.findbyid.failed", "", "orderid", orderId, "statusCode", 404));
            }

            return order;
        }
        catch (Exception e) {
            Map error = warn("product.findbyid.failed", e.getMessage(), "orderid", orderId, "statusCode", 500);
            return new Order().error(error);
        }
    }

}
