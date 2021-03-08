package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.Order;
import by.panasenko.flowershop.model.OrderFlower;

import java.util.List;

public interface OrderFlowerService {
    void save(OrderFlower orderFlower);
    List<OrderFlower> getOrderFlowerListByOrder(Order order);
}
