package com.scottwseo.dvdstore.jdbi;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by seos on 9/19/16.
 */
public class OrderAndOrderLine {

    private Long orderId;

    private String orderDate;

    private Long customerId;

    private BigDecimal netAmount;

    private BigDecimal tax;

    private BigDecimal totalAmount;

    private Long orderLineId;

    private Long productId;

    private int quantity;

    private Map error = null;

    public <T> T error(Map error) {
        this.error = error;
        return (T) this;
    }

    public Map error() {
        return this.error;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderAndOrderLine orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public OrderAndOrderLine orderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderAndOrderLine customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public OrderAndOrderLine netAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
        return this;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderAndOrderLine tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderAndOrderLine totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public OrderAndOrderLine orderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public OrderAndOrderLine productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderAndOrderLine quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Map getError() {
        return error;
    }

    public OrderAndOrderLine setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderAndOrderLine setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderAndOrderLine setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderAndOrderLine setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
        return this;
    }

    public OrderAndOrderLine setTax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public OrderAndOrderLine setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public OrderAndOrderLine setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
        return this;
    }

    public OrderAndOrderLine setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public OrderAndOrderLine setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

}
