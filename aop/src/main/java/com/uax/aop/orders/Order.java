package com.uax.aop.orders;

public class Order {

    private Long id;
    private double total;
    private String customerName;

    public Order(Long id, double total, String customerName) {
        this.id = id;
        this.total = total;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", total=" + total +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
