package com.flowershop.repository;

import com.flowershop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getOne(Integer id);
}
