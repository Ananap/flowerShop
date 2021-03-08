package com.flowershop.service.impl;

import com.flowershop.model.Basket;
import com.flowershop.model.BasketFlower;
import com.flowershop.repository.BasketFlowerRepository;
import com.flowershop.service.BasketFlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketFlowerServiceImpl implements BasketFlowerService {
    @Autowired
    private BasketFlowerRepository basketFlowerRepository;

    public List<BasketFlower> findByBasket(Basket basket) {
        return basketFlowerRepository.findByBasket(basket);
    }

    public void remove(Integer id) {
        basketFlowerRepository.deleteById(id);
    }

    public BasketFlower findById(Integer id) {
        return basketFlowerRepository.getOne(id);
    }

    public void save(BasketFlower basketFlower) {
        basketFlowerRepository.save(basketFlower);
    }
}