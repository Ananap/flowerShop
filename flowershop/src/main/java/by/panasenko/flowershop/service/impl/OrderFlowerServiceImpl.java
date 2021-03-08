package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.model.Order;
import by.panasenko.flowershop.model.OrderFlower;
import by.panasenko.flowershop.repository.OrderFlowerRepository;
import by.panasenko.flowershop.service.OrderFlowerService;
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
