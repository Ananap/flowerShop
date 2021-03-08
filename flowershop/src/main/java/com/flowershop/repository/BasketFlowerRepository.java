package com.flowershop.repository;

import com.flowershop.model.Basket;
import com.flowershop.model.BasketFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketFlowerRepository extends JpaRepository<BasketFlower, Integer> {
    List<BasketFlower> findByBasket(Basket basket);
    void deleteById(Integer id);
    BasketFlower getOne(Integer id);
}
