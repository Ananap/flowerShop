package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.Storage;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.FlowerPageCriteria;
import by.panasenko.flowershop.model.product.FlowerSearchCriteria;
import by.panasenko.flowershop.service.impl.FlowerServiceImpl;
import by.panasenko.flowershop.service.impl.FlowerTypeServiceImpl;
import by.panasenko.flowershop.service.impl.StorageServiceImpl;
import by.panasenko.flowershop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class FlowerController {
    @Autowired
    private FlowerServiceImpl flowerServiceImpl;

    @Autowired
    private FlowerTypeServiceImpl flowerTypeServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private StorageServiceImpl storageServiceImpl;

/*    @GetMapping("/findItem")
    public String findItem(@ModelAttribute("keyword") String keyword,
                           Model model) {
        List<FlowerType> flowerTypeList = flowerTypeService.findAll();
        model.addAttribute("flowerTypeList", flowerTypeList);
        List<Flower> flowerListByKeyword = flowerService.findByKeyword(keyword);
        model.addAttribute("flowerList", flowerListByKeyword);
        return "itemPage";
    }*/

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
        Page<Flower> page = flowerServiceImpl.findAll(flowerPageCriteria);
        flowerServiceImpl.fillModel(flowerPageCriteria, page, model);
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
            User user = userServiceImpl.findByEmail(email);
            model.addAttribute("user", user);
        }
        Flower flower = flowerServiceImpl.findOne(id);
        model.addAttribute("flower", flower);
        Storage storage = storageServiceImpl.findByFlower(flower);

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
