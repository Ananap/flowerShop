package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.Storage;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.FlowerPageCriteria;
import by.panasenko.flowershop.model.product.FlowerSearchCriteria;
import by.panasenko.flowershop.service.FlowerService;
import by.panasenko.flowershop.service.StorageService;
import by.panasenko.flowershop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class FlowerController {
    @Autowired
    private FlowerService flowerService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/findItem")
    public String findItem(@ModelAttribute("keyword") String keyword,
                           Model model) {
        return listItemPage(model, 1, "name", "asc", 3, keyword, null, null, null);
    }

    @GetMapping("/itemPage")
    public String itemPage(Model model){
        return listItemPage(model, 1, "name", "asc", 3, null, null, null, null);
    }

    @GetMapping("/listItem")
    public String listItemPage(Model model,
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
        model.addAttribute("url", "/listItem");
        return "itemPage";
    }

    @GetMapping("/flowerDetail")
    public String flowerDetail(@RequestParam("id") Integer id,
                               @RequestParam(name = "basket", required = false, defaultValue = "false") Boolean basket,
                               @RequestParam(name = "stock", required = false, defaultValue = "false") Boolean stock,
                               Model model,
                               Principal principal) throws ShopException {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        Flower flower = flowerService.findOne(id);
        model.addAttribute("flower", flower);
        Storage storage = storageService.findByFlower(flower);

        model.addAttribute("flowerType", flower.getFlowerType());
        model.addAttribute("storage", storage);
        model.addAttribute("amount", 1);
        if (basket) {
            model.addAttribute("addFlowerSuccess", true);
        }
        if (stock) {
            model.addAttribute("notEnoughStorage", true);
        }
        return "flowerDetail";
    }
}
