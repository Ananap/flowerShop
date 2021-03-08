package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.BasketFlower;

import java.util.List;

public interface BasketFlowerService {
    List<BasketFlower> findByBasket(Basket basket);
    void remove(Integer id);
    BasketFlower findById(Integer id);
    void save(BasketFlower basketFlower);
}
