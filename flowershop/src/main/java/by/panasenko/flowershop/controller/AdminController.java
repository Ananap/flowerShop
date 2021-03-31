package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.Order;
import by.panasenko.flowershop.model.Status;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.FlowerPageCriteria;
import by.panasenko.flowershop.model.product.FlowerSearchCriteria;
import by.panasenko.flowershop.service.*;
import by.panasenko.flowershop.util.PagePath;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private static final Logger logger = LogManager.getLogger(AdminController.class);
    private final static String PATHNAME = "src/main/resources/static/image/flower/";

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private FlowerTypeService flowerTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private OrderService orderService;

    @GetMapping("/changeStatus")
    public String changeStatus(@ModelAttribute("id") Integer id,
                               @ModelAttribute("statusOrder") String statusOrder) {
        Order order = orderService.getOne(id);
        order.setStatusOrder(statusOrder.equals("APPROVED") ? Status.APPROVED : statusOrder.equals("INPROCESS") ? Status.INPROCESS : Status.REJECTED);
        orderService.save(order);
        User user = order.getUser();
        if (order.getStatusOrder() == Status.APPROVED) {
            mailSender.sendMimeMessage(mailSender.constructOrderConfirmationEmail(user, order));
        } else if (order.getStatusOrder() == Status.REJECTED) {
            mailSender.send(user.getEmail(), "My FlowerShop", MailSender.orderRejected(user.getUsername()));
        }
        return PagePath.ORDER_INFO;
    }

    @GetMapping("/createAdmin")
    public String createAdmin() {
        return PagePath.ADD_ADMIN;
    }

    @PostMapping("/createAdmin")
    public String createAdminPost(@ModelAttribute("email") String userEmail,
                                  @ModelAttribute("username") String username,
                                  Model model) {
        if(userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);
            return PagePath.ADD_ADMIN;
        }
        if(userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);
            return PagePath.ADD_ADMIN;
        }
        String password = userService.generateRandomPassword();
        User user = userService.createUser(username, userEmail, password, "ADMIN");
        String token = UUID.randomUUID().toString();
        userService.createPasswordTokenForUser(user, token);
        mailSender.send(user.getEmail(), "My FlowerShop", MailSender.messageCreateAdmin(user.getUsername(), token, password));
        model.addAttribute("emailSent", true);
        logger.info("Created new account");
        return PagePath.ADD_ADMIN;
    }

    @GetMapping("/flowerList")
    public String flowerList(Model model){
        return listByPage(model, 1, "name", "asc", 3, null, null, null, null);
    }

    @GetMapping("/page")
    public String listByPage(Model model,
                             @RequestParam("pageNumber") int currentPage,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             @RequestParam("size") Integer size,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "category", required = false) Integer category,
                             @RequestParam(value = "priceFrom", required = false) Double priceFrom,
                             @RequestParam(value = "priceTo", required = false) Double priceTo) {
        FlowerSearchCriteria flowerSearchCriteria = new FlowerSearchCriteria(keyword, category, priceFrom, priceTo);
        FlowerPageCriteria flowerPageCriteria = new FlowerPageCriteria(currentPage, size, FlowerPageCriteria.sortDirection(sortDir), sortField, flowerSearchCriteria);
        Page<Flower> page = flowerService.findAll(flowerPageCriteria);
        flowerService.fillModel(flowerPageCriteria, page, model);
        model.addAttribute("url", "/page");
        return PagePath.FLOWER_LIST;
    }

    @GetMapping("/itemInfo")
    public String flowerInfo(@RequestParam("id") Integer id, Model model) throws ShopException {
        Flower flower = flowerService.findOne(id);
        model.addAttribute("flower", flower);
        return PagePath.ITEM_INFO;
    }

    @GetMapping("/updateItem")
    public String updateItem(@RequestParam("id") Integer id, Model model) throws ShopException {
        Flower flower = flowerService.findOne(id);
        model.addAttribute("flower", flower);
        model.addAttribute("flowerTypeList", flowerTypeService.findAll());
        return PagePath.UPDATE_ITEM;
    }

    @PostMapping("/updateItem")
    public String updateItemPost(@ModelAttribute("flower") Flower flower,
                                 @ModelAttribute("image") MultipartFile image) {
        String name = flower.getId()+".png";
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                Files.delete(Paths.get(PATHNAME + name));
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(PATHNAME + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                logger.error("update item error: ", e);
            }
        }
        flower.setFlowerImage(name);
        flowerService.save(flower);
        logger.info("Update item successfully");
        return PagePath.FLOWER_LIST_REDIRECT;
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("id") Integer id) {
        flowerService.deleteFlower(id);
        return PagePath.FLOWER_LIST_REDIRECT;
    }
}
