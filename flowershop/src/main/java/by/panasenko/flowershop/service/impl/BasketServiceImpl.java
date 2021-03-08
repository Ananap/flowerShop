package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.BasketFlower;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.repository.BasketFlowerRepository;
import by.panasenko.flowershop.repository.BasketRepository;
import by.panasenko.flowershop.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketFlowerRepository basketFlowerRepository;

    public List<BasketFlower> basketFlowerList(Basket basket) {
        return basketFlowerRepository.findByBasket(basket);
    }

    public Basket findById(Integer id) {
        return basketRepository.getOne(id);
    }

    public void updateBasket(Basket basket) {
        BigDecimal basketTotal = new BigDecimal(0);
        List<BasketFlower> basketFlowerList = basketFlowerList(basket);
        for(BasketFlower basketFlower: basketFlowerList) {
            if(basketFlower.getFlower().getStorage().getCount() > 0) {
                updateBasketFlower(basketFlower);
                basketTotal = basketTotal.add(basketFlower.getSubTotal());
            }
        }
        basket.setTotalCost(basketTotal);
        basketRepository.save(basket);
    }

    private void updateBasketFlower(BasketFlower basketFlower) {
        BigDecimal bigDecimal = new BigDecimal(basketFlower.getFlower().getPrice()).multiply(new BigDecimal(basketFlower.getCount()));
        bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        basketFlower.setSubTotal(bigDecimal);
        basketFlowerRepository.save(basketFlower);
    }

    public void addFlowerToBasket(Flower flower, User user, int count) {
        List<BasketFlower> basketFlowers = basketFlowerList(user.getBasket());
        for(BasketFlower basketFlower: basketFlowers) {
            if(flower.getId() == basketFlower.getFlower().getId()) {
                basketFlower.setCount(basketFlower.getCount()+count);
                basketFlower.setSubTotalCount(flower.getPrice(), count);
                basketFlowerRepository.save(basketFlower);
                return;
            }
        }
        BasketFlower basketFlower = new BasketFlower();
        basketFlower.setBasket(user.getBasket());
        basketFlower.setCount(count);
        basketFlower.setFlower(flower);
        basketFlower.setSubTotalCount(flower.getPrice(), count);
        basketFlowerRepository.save(basketFlower);
    }

    public void clearBasket(Basket basket) {
        List<BasketFlower> basketFlowerList = basketFlowerList(basket);
        for (BasketFlower basketFlower : basketFlowerList) {
            basketFlowerRepository.delete(basketFlower);
        }
        basket.setTotalCost(new BigDecimal(0));
        basketRepository.save(basket);
    }
}
