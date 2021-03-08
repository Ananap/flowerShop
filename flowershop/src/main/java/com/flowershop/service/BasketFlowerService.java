package com.flowershop.service;

import com.flowershop.model.Basket;
import com.flowershop.model.BasketFlower;

import java.util.List;

public interface BasketFlowerService {
    List<BasketFlower> findByBasket(Basket basket);
    void remove(Integer id);
    BasketFlower findById(Integer id);
    void save(BasketFlower basketFlower);
}
