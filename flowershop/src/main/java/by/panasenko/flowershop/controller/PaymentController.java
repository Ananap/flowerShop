package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.Payment;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.service.PaymentService;
import by.panasenko.flowershop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class PaymentController {
    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/listOfCreditCard")
    public String listOfCreditCards(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("activePayment", true);
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserInfo().getPayments());
        model.addAttribute("userAddress", user.getUserInfo().getAddress());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("orderList", user.getOrders());
        return "myProfile";
    }

    @GetMapping("/addNewCreditCard")
    public String addNewCreditCard(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("activePayment", true);
        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("orderList", user.getOrders());
        Payment payment = new Payment();
        model.addAttribute("userPayment", payment);
        return "myProfile";
    }

    @PostMapping("/addNewCreditCard")
    public String addNewCreditCardPost(Model model, @ModelAttribute("userPayment") Payment userPayment, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        paymentService.addUserPayment(userPayment, user.getUserInfo());
        model.addAttribute("user", user);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("orderList", user.getOrders());
        model.addAttribute("userPaymentList", user.getUserInfo().getPayments());
        logger.info("User " + user.getUsername() + " added payment");
        return "redirect:myProfile";
    }

    @GetMapping("/updateCreditCard")
    public String updateCreditCard(Model model, Principal principal, @RequestParam("id") Integer id) {
        User user = userService.findByEmail(principal.getName());
        Payment userPayment = paymentService.findById(id);
        if(user.getUserInfo().getId() != userPayment.getUserInfo().getId()) {
            throw new ShopException("Payment doesn't belong to user!");
        } else {
            model.addAttribute("user", user);
            model.addAttribute("userPayment", userPayment);
            model.addAttribute("activePayment", true);
            model.addAttribute("addNewCreditCard", true);
            model.addAttribute("orderList", user.getOrders());
            return "myProfile";
        }
    }

    @GetMapping("/removeCreditCard")
    public String removeCreditCard(Model model, Principal principal, @RequestParam("id") Integer id) {
        User user = userService.findByEmail(principal.getName());
        Payment userPayment = paymentService.findById(id);
        if(user.getUserInfo().getId() != userPayment.getUserInfo().getId()) {
            throw new ShopException("Payment doesn't belong to user!");
        } else {
            model.addAttribute("user", user);
            paymentService.removeById(id);
            model.addAttribute("activePayment", true);
            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("userPaymentList", user.getUserInfo().getPayments());
            model.addAttribute("orderList", user.getOrders());
            logger.info("User" + user.getUsername() + " removed credit card");
            return "myProfile";
        }
    }

    @PostMapping("/setDefaultPayment")
    public String setDefaultPayment(Model model, Principal principal, @ModelAttribute("defaultUserPaymentId") Integer defaultUserPaymentId) {
        User user = userService.findByEmail(principal.getName());
        paymentService.setDefaultPayment(defaultUserPaymentId);
        model.addAttribute("activePayment", true);
        model.addAttribute("user", user);
        model.addAttribute("orderList", user.getOrders());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("userPaymentList", user.getUserInfo().getPayments());
        return "myProfile";
    }

    @GetMapping("/setPaymentMethod")
    public String setPaymentMethod(@RequestParam("userPaymentId") Integer userPaymentId,
                                   Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Basket basket = user.getBasket();
        Payment payment = paymentService.findById(userPaymentId);
        if (payment.getUserInfo().getId() != user.getUserInfo().getId()) {
            throw new ShopException("Payment doesn't belong to user!");
        } else {
            paymentService.setDefaultPayment(userPaymentId);
            return "redirect:/checkout?id="+basket.getId();
        }
    }
}
