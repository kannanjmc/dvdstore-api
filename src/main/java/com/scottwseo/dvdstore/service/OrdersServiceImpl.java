package com.scottwseo.dvdstore.service;

import com.google.inject.Inject;
import com.scottwseo.commons.util.JsonUtil;
import com.scottwseo.dvdstore.api.Order;
import com.scottwseo.dvdstore.api.OrderLine;
import com.scottwseo.dvdstore.api.Products;
import com.scottwseo.dvdstore.jdbi.OrderAndOrderLine;
import com.scottwseo.dvdstore.jdbi.OrderMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.PreparedBatch;
import org.skife.jdbi.v2.util.LongColumnMapper;

import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.*;

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
            .bind("orderdate", JsonUtil.format(order.getOrderDate()))
            .bind("customerid", order.getCustomerId())
            .bind("netamount", order.getNetAmount())
            .bind("tax", order.getTax())
            .bind("totalamount", order.getTotalAmount())
            .executeAndReturnGeneratedKeys(LongColumnMapper.PRIMITIVE).first();

            PreparedBatch b = h.prepareBatch("INSERT\n" +
                    "INTO\n" +
                    "    orderlines\n" +
                    "    (\n" +
                    "        orderlineid,\n" +
                    "        orderid,\n" +
                    "        prod_id,\n" +
                    "        quantity,\n" +
                    "        orderdate\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (\n" +
                    "        :orderlineid,\n" +
                    "        :orderid,\n" +
                    "        :prod_id,\n" +
                    "        :quantity,\n" +
                    "        :orderdate\n" +
                    "    )");

            for (OrderLine orderline : order.getOrderlines()) {
                int orderlineid = 1;
                b.add()
                .bind("orderlineid", orderlineid++)
                .bind("orderid", orderId)
                .bind("prod_id", orderline.getProductId())
                .bind("quantity", orderline.getQuantity())
                .bind("orderdate", JsonUtil.format(orderline.getOrderDate()));
                info("batchinsert", "", "orderline", orderline);
            }
            b.execute();

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
                            "    o.orderid,\n" +
                            "    o.orderdate,\n" +
                            "    customerid,\n" +
                            "    netamount,\n" +
                            "    tax,\n" +
                            "    totalamount,\n" +
                            "    orderlineid,\n" +
                            "    prod_id,\n" +
                            "    quantity,\n" +
                            "    ol.orderdate\n" +
                            "FROM\n" +
                            "    orders o INNER JOIN orderlines ol ON (o.orderid = ol.orderid)\n" +
                            "WHERE\n" +
                            "    o.orderid = :orderid";
            List<OrderAndOrderLine> orderAndOrderLines = h.createQuery(sql.toString())
                    .bind("orderid", orderId)
                    .map(new OrderMapper())
                    .list();

            if (orderAndOrderLines == null || orderAndOrderLines.size() == 0) {
                return new Order().error(map("order.findbyid.failed", "", "orderid", orderId, "statusCode", 404));
            }

            OrderAndOrderLine row = orderAndOrderLines.get(0);
            Order order = new Order().orderId(row.getOrderId())
                    .orderDate(row.getOrderDate())
                    .customerId(row.getCustomerId())
                    .netAmount(row.getNetAmount())
                    .tax(row.getTax())
                    .totalAmount(row.getTotalAmount());

            for (OrderAndOrderLine ool : orderAndOrderLines) {
                order.getOrderlines().add(
                        new OrderLine()
                            .orderLineId(ool.getOrderLineId())
                            .productId(ool.getProductId())
                            .quantity(ool.getQuantity())
                            .orderDate(ool.getOrderDate())
                );
            }

            return order;
        }
        catch (Exception e) {
            Map error = warn("product.findbyid.failed", e.getMessage(), "orderid", orderId, "statusCode", 500);
            return new Order().error(error);
        }
    }

}
