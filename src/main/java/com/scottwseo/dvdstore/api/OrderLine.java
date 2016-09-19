package com.scottwseo.dvdstore.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by seos on 9/19/16.
 */
public class OrderLine {

    private Long orderLineId;

    private Long productId;

    private int quantity;

    private String orderDate;

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    @JsonProperty("product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("order_date")
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
