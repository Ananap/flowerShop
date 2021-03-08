package com.flowershop.repository;

import com.flowershop.model.Basket;
import com.flowershop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Basket findByUser(User user);
    Basket getOne(Integer id);
    void delete(Basket basket);
    Basket save(Basket s);
}
