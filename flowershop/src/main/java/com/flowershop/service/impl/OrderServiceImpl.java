package com.flowershop.service.impl;

import com.flowershop.model.*;
import com.flowershop.model.product.Flower;
import com.flowershop.repository.OrderRepository;
import com.flowershop.repository.PaymentRepository;
import com.flowershop.service.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderFlowerServiceImpl orderFlowerServiceImpl;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BasketFlowerServiceImpl basketFlowerServiceImpl;

    @Transactional
    public Order createOrder(
            Basket basket,
            String address,
            Payment payment,
            String date,
            String time,
            String cash) {
        User user = basket.getUser();
        Order order;
        if (cash.isEmpty()) {
            payment.setUserInfo(user.getUserInfo());
            payment.setDefaultPayment(true);
            if (payment.getId() != 0) {
                Payment payment1 = paymentRepository.getOne(payment.getId());
                payment.setCardName(payment1.getCardName());
            } else {
                payment.setCardName("Standard name");
            }
            paymentRepository.save(payment);
            order = new Order(false, address, Calendar.getInstance().getTime(), parseOrderDate(date), time, basket.getTotalCost(), user, payment, false);
        } else {
            order = new Order(false, address, Calendar.getInstance().getTime(), parseOrderDate(date), time, basket.getTotalCost(), user, null, true);
        }
        List<OrderFlower> orderFlowerList = new ArrayList<>();
        List<BasketFlower> basketFlowerList = basketFlowerServiceImpl.findByBasket(basket);
        for (BasketFlower basketFlower : basketFlowerList) {
            Flower flower = basketFlower.getFlower();
            OrderFlower orderFlower = new OrderFlower();
            orderFlower.setOrder(order);
            orderFlower.setFlower(flower);
            orderFlower.setCount(basketFlower.getCount());
            orderFlower.setSubTotal(basketFlower.getSubTotal());
            orderFlowerServiceImpl.save(orderFlower);
            orderFlowerList.add(orderFlower);
            Storage storageFlower = flower.getStorage();
            storageFlower.setCount(storageFlower.getCount() - basketFlower.getCount());
        }
        order.setOrderFlower(orderFlowerList);
        if (cash.isEmpty()) {
            payment.setOrders(Collections.singletonList(order));
        }
        order = orderRepository.save(order);
        return order;
    }

    public Order findOne(Integer id) {
        return orderRepository.getOne(id);
    }

    public Date parseOrderDate(String date) {
        SimpleDateFormat formatTemplate = new SimpleDateFormat("yyyy-MM-dd");
        Date parsingDate = null;
        try {
            parsingDate = formatTemplate.parse(date);
        } catch (ParseException e) {
            logger.error("Can't parse order date", e);
        }
        return parsingDate;
    }
}
