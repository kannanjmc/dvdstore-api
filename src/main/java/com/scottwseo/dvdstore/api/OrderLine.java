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

    @JsonProperty("orderline_id")
    public Long getOrderLineId() {
        return orderLineId;
    }

    public OrderLine orderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
        return this;
    }

    @JsonProperty("product_id")
    public Long getProductId() {
        return productId;
    }

    public OrderLine productId(Long productId) {
        this.productId = productId;
        return this;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    public OrderLine quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getOrderDate() {
        return orderDate;
    }

    @JsonProperty("order_date")
    public OrderLine orderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

}
