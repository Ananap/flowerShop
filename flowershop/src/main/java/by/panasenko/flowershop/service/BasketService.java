package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.BasketFlower;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.product.Flower;

import java.util.List;

public interface BasketService {
    List<BasketFlower> basketFlowerList(Basket basket);
    Basket findById(Integer id);
    void updateBasket(Basket basket);
    void addFlowerToBasket(Flower flower, User user, int count);
    void clearBasket(Basket basket);
}
