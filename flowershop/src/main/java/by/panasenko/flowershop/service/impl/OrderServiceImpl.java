package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.model.*;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.PageCriteria;
import by.panasenko.flowershop.repository.OrderRepository;
import by.panasenko.flowershop.repository.PaymentRepository;
import by.panasenko.flowershop.service.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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
            order = new Order(Status.INPROCESS, address, Calendar.getInstance().getTime(), parseOrderDate(date), time, basket.getTotalCost(), user, payment, false);
        } else {
            order = new Order(Status.INPROCESS, address, Calendar.getInstance().getTime(), parseOrderDate(date), time, basket.getTotalCost(), user, null, true);
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

    public Page<Order> findAllOrder(PageCriteria pageCriteria) {
        Pageable pageable = buildPageOrder(pageCriteria);
        return orderRepository.findAll(pageable);
    }

    public Pageable buildPageOrder(PageCriteria pageCriteria) {
        Sort sort = Sort.by(pageCriteria.getSortDir(), pageCriteria.getSortBy());
        Pageable pageable = PageRequest.of((pageCriteria.getPageNumber() - 1), pageCriteria.getPageSize(), sort);
        return pageable;
    }

    public void fillModelOrder(PageCriteria orderPageCriteria, Page<Order> page, Model model) {
        List<Order> orderList = page.getContent();
        List<Integer> sizeList = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 10));
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("size", orderPageCriteria.getPageSize());
        model.addAttribute("orderList", orderList);
        if (orderList.size() == 0) {
            model.addAttribute("emptyList", true);
        }
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", orderPageCriteria.getPageNumber());
        model.addAttribute("sortField", orderPageCriteria.getSortBy());
        model.addAttribute("sortDir", orderPageCriteria.getSortDir());
        String reverseSortDir = orderPageCriteria.getSortDir() == Sort.Direction.ASC ? "asc" : "desc";
        model.addAttribute("reverseSortDir", reverseSortDir);
    }

    public Order getOne(Integer id) {
        return orderRepository.getOne(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
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
