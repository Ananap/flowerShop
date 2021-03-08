package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.Order;
import by.panasenko.flowershop.model.OrderFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderFlowerRepository extends JpaRepository<OrderFlower, Integer> {
    OrderFlower save (OrderFlower orderFlower);
    List<OrderFlower> findByOrder(Order order);
}
