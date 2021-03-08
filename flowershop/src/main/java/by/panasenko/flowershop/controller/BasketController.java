package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.Basket;
import by.panasenko.flowershop.model.BasketFlower;
import by.panasenko.flowershop.model.Payment;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.service.BasketFlowerService;
import by.panasenko.flowershop.service.BasketService;
import by.panasenko.flowershop.service.FlowerService;
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
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class BasketController {
    private static final Logger logger = LogManager.getLogger(BasketController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private BasketFlowerService basketFlowerService;

    @GetMapping("/basket")
    public String basket(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        Basket basket = user.getBasket();
        List<BasketFlower> basketFlowerList = basketService.basketFlowerList(basket);
        basketService.updateBasket(basket);
        model.addAttribute("basketFlowerList", basketFlowerList);
        model.addAttribute("basket", basket);
        return "basket";
    }

    @PostMapping("/basketUpdate")
    public String basketUpdate(@RequestParam("id") Integer id,
                               @RequestParam("count") Integer count) {
        BasketFlower basketFlower = basketFlowerService.findById(id);
        basketFlower.setCount(count);
        basketFlowerService.save(basketFlower);
        logger.info("Basket updated");
        return "redirect:/basket";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("id") Integer basketId,
                           @RequestParam(value = "missingRequiredField", required = false, defaultValue = "false") boolean missingRequiredField,
                           Model model,
                           Principal principal){
        User user = userService.findByEmail(principal.getName());
        if (basketId != user.getBasket().getId()) {
            return "badRequest";
        }
        List<BasketFlower> basketFlowerList = basketFlowerService.findByBasket(user.getBasket());
        if (basketFlowerList.size() == 0) {
            model.addAttribute("emptyBasket", true);
            return "forward:/basket";
        }
        for (BasketFlower basketFlower : basketFlowerList) {
            if (basketFlower.getFlower().getStorage().getCount() < basketFlower.getCount()) {
                model.addAttribute("notEnoughStorage", true);
                return "forward:/basket";
            }
        }
        List<Payment> paymentList = user.getUserInfo().getPayments();
        if (paymentList.size() == 0) {
            Payment payment = new Payment();
            model.addAttribute("emptyPaymentList", true);
            model.addAttribute("userPayment", payment);
        } else {
            model.addAttribute("emptyPaymentList", false);
            boolean isDefault = false;
            for (Payment payment : paymentList) {
                if (payment.isDefaultPayment()) {
                    model.addAttribute("userPayment", payment);
                    isDefault = true;
                }
            }
            if(!isDefault){
                model.addAttribute("userPayment", paymentList.get(0));
            }
        }
        if (missingRequiredField) {
            model.addAttribute("missingRequiredField", true);
        }
        model.addAttribute("user", user);
        model.addAttribute("basket", basketService.findById(basketId));
        model.addAttribute("basketFlowerList", basketFlowerList);
        model.addAttribute("paymentList", paymentList);
        return "checkout";
    }

    @PostMapping("/addItemToBasket")
    public String addItemToBasket(Model model,
                                  Principal principal,
                                  @ModelAttribute("flower") Flower flower,
                                  @ModelAttribute("amount") Integer count) throws ShopException {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        flower = flowerService.findOne(flower.getId());
        if(count > flower.getStorage().getCount()) {
            return "redirect:/flowerDetail?stock=true&id="+flower.getId();
        }
        basketService.addFlowerToBasket(flower, user, count);
        logger.info("Item " + flower.getName() + " is added to basket");
        return "redirect:/flowerDetail?basket=true&id="+flower.getId();
    }

    @GetMapping("/removeItem")
    public String removeItem(@RequestParam("id") Integer basketFlowerId,
                             Model model) {
        basketFlowerService.remove(basketFlowerId);
        model.addAttribute("removeItem", true);
        return "forward:/basket";
    }
}
