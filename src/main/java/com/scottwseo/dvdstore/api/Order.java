package com.scottwseo.dvdstore.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by seos on 9/19/16.
 */
public class Order {

    private Long orderId;

    private String orderDate;

    private Long customerId;

    private BigDecimal netAmount;

    private BigDecimal tax;

    private BigDecimal totalAmount;

    private List<OrderLine> orderlines = new ArrayList();

    private Map error = null;

    public <T> T error(Map error) {
        this.error = error;
        return (T) this;
    }

    public Map error() {
        return this.error;
    }

    public Order orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    @JsonProperty("order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Order orderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    @JsonProperty("order_date")
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Order customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    @JsonProperty("customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Order netAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
        return this;
    }

    @JsonProperty("net_amount")
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public Order tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    @JsonProperty("tax")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Order totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    @JsonProperty("total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonProperty("orderlines")
    public List<OrderLine> getOrderlines() {
        return orderlines;
    }

    public void setOrderlines(List<OrderLine> orderlines) {
        this.orderlines = orderlines;
    }



}
