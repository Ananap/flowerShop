package com.flowershop.controller;

import com.flowershop.model.*;
import com.flowershop.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketFlowerService basketFlowerService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderFlowerService orderFlowerService;

    @PostMapping("/checkout")
    public String checkoutPost(@ModelAttribute("address") String address,
                               @ModelAttribute("cash") String yes,
                               @ModelAttribute("payment") Payment payment,
                               @ModelAttribute("date") String date,
                               @ModelAttribute("time") String time,
                               Model model,
                               Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Basket basket = user.getBasket();
        List<BasketFlower> basketFlowerList = basketFlowerService.findByBasket(basket);
        model.addAttribute("basketFlowerList", basketFlowerList);
        if (yes.isEmpty()) {
            if (payment.hasNullFields()) {
                return "redirect:/checkout?missingRequiredField=true&id=" + basket.getId();
            }
        }
        if (address.isEmpty() || date.isEmpty() || time.isEmpty()) {
            return "redirect:/checkout?missingRequiredField=true&id=" + basket.getId();
        }
        Order order = orderService.createOrder(basket, address, payment, date, time, yes);
        mailSender.sendMimeMessage(mailSender.constructOrderConfirmationEmail(user, order));
        basketService.clearBasket(basket);
        model.addAttribute("order", order);
        model.addAttribute("orderFlowerList", orderFlowerService.getOrderFlowerListByOrder(order));
        logger.info("User" + user.getUsername() + " make an order");
        return "orderSubmittedPage";
    }

    @GetMapping("/orderDetail")
    public String orderDetail (@RequestParam("id") Integer id,
                               Principal principal,
                               Model model) {
        User user = userService.findByEmail(principal.getName());
        Order order = orderService.findOne(id);

        if (order.getUser() != user) {
            return "badRequest";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("activeOrders", true);
            model.addAttribute("userPaymentList", user.getUserInfo().getPayments());
            model.addAttribute("userAddress", user.getUserInfo().getAddress());
            model.addAttribute("orderList", user.getOrders());
            model.addAttribute("order", order);
            if (order.getPayment() == null) {
                model.addAttribute("cash", true);
            }
            else {
                model.addAttribute("cash", false);
            }
            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("listOfAddress", true);
            model.addAttribute("displayOrderDetail", true);
            List<OrderFlower> orderFlowerList = orderFlowerService.getOrderFlowerListByOrder(order);
            model.addAttribute("orderFlowerList", orderFlowerList);
            return "myProfile";
        }
    }
}
