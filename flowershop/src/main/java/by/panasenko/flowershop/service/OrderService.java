package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.Order;
import by.panasenko.flowershop.model.Payment;

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
