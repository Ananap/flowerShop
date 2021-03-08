package com.flowershop.service;

import com.flowershop.model.Basket;
import com.flowershop.model.BasketFlower;
import com.flowershop.model.User;
import com.flowershop.model.product.Flower;

import java.util.List;

public interface BasketService {
    List<BasketFlower> basketFlowerList(Basket basket);
    Basket findById(Integer id);
    void updateBasket(Basket basket);
    void addFlowerToBasket(Flower flower, User user, int count);
    void clearBasket(Basket basket);
}
