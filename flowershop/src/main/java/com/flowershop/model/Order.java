package com.flowershop.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "flowershop", name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean statusOrder;
    private Date dateDelivery;
    private BigDecimal totalCost;
    private String timeOrder;
    private String address;
    private Date dateOrder;
    private boolean cash;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderFlower> orderFlower;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Order(){}

    public Order(boolean status, String address, Date dateOrder, Date dateDelivery, String time, BigDecimal totalCost, User user, Payment payment, boolean cash) {
        this.statusOrder = status;
        this.address = address;
        this.dateOrder = dateOrder;
        this.dateDelivery = dateDelivery;
        this.timeOrder = time;
        this.totalCost = totalCost;
        this.user = user;
        this.payment = payment;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(boolean statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderFlower> getOrderFlower() {
        return orderFlower;
    }

    public void setOrderFlower(List<OrderFlower> orderFlower) {
        this.orderFlower = orderFlower;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }
}
