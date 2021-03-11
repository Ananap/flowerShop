package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.Order;
import by.panasenko.flowershop.model.Payment;
import by.panasenko.flowershop.model.product.PageCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.Date;

public interface OrderService {
    Order findOne(Integer id);
    Date parseOrderDate(String date);
    Order createOrder(
            Basket basket,
            String address,
            Payment payment,
            String date,
            String time,
            String cash);
    Page<Order> findAllOrder(PageCriteria pageCriteria);
    Pageable buildPageOrder(PageCriteria pageCriteria);
    void fillModelOrder(PageCriteria PageCriteria, Page<Order> page, Model model);
    Order getOne(Integer id);
    Order save(Order order);
}
