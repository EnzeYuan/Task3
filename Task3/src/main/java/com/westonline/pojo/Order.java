package com.westonline.pojo;

import java.util.Date;

public class Order {
    private int orderId;
    private int[] orderItems;
    private Date orderTime;
    private double orderPrice;

    public Order() {
    }

    public Order(int orderId,int[] orderItems, Date orderTime, double orderPrice) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.orderTime = orderTime;
        this.orderPrice = orderPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(int[] orderItems) {
        this.orderItems = orderItems;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }


}
