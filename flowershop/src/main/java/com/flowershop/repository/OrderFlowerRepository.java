package com.flowershop.repository;

import com.flowershop.model.Order;
import com.flowershop.model.OrderFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderFlowerRepository extends JpaRepository<OrderFlower, Integer> {
    OrderFlower save (OrderFlower orderFlower);
    List<OrderFlower> findByOrder(Order order);
}
