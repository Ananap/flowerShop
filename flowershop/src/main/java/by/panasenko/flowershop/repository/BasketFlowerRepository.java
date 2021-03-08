package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.BasketFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketFlowerRepository extends JpaRepository<BasketFlower, Integer> {
    List<BasketFlower> findByBasket(Basket basket);
    void deleteById(Integer id);
    BasketFlower getOne(Integer id);
}
