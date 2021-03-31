package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Basket getOne(Integer id);
    void delete(Basket basket);
    Basket save(Basket s);
}
