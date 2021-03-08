package com.flowershop.service.impl;

import com.flowershop.model.Order;
import com.flowershop.model.OrderFlower;
import com.flowershop.repository.OrderFlowerRepository;
import com.flowershop.service.OrderFlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFlowerServiceImpl implements OrderFlowerService {
    @Autowired
    private OrderFlowerRepository orderFlowerRepository;

    public void save (OrderFlower orderFlower) {
        orderFlowerRepository.save(orderFlower);
    }

    public List<OrderFlower> getOrderFlowerListByOrder(Order order){
        return orderFlowerRepository.findByOrder(order);
    }
}
