package com.flowershop.service;

import com.flowershop.model.Basket;
import com.flowershop.model.Order;
import com.flowershop.model.Payment;

import java.util.Date;

public interface OrderService {
    Order findOne(Integer id);
    Date parseOrderDate(String date);
    Order createOrder(
            Basket basket,
            String address,
            Payment payment,
            String date,
            String time,
            String cash);
}
