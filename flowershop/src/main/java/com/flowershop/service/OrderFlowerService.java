package com.flowershop.service;

import com.flowershop.model.Order;
import com.flowershop.model.OrderFlower;

import java.util.List;

public interface OrderFlowerService {
    void save(OrderFlower orderFlower);
    List<OrderFlower> getOrderFlowerListByOrder(Order order);
}
