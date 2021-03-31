package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.FlowerType;
import by.panasenko.flowershop.model.Storage;
import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.FlowerPageCriteria;
import by.panasenko.flowershop.model.product.FlowerSearchCriteria;
import by.panasenko.flowershop.service.FlowerService;
import by.panasenko.flowershop.service.FlowerTypeService;
import by.panasenko.flowershop.service.StorageService;
import by.panasenko.flowershop.service.UserService;
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
import java.security.Principal;
import java.util.List;

@Controller
public class FlowerController {
    private static final Logger logger = LogManager.getLogger(FlowerController.class);
    private final static String PATHNAME = "src/main/resources/static/image/flower/";
    @Autowired
    private FlowerService flowerService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private FlowerTypeService flowerTypeService;

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
        return PagePath.ITEM_PAGE;
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
        return PagePath.FLOWER_DETAIL;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/addItem")
    public String addItem(Model model) {
        Flower flower = new Flower();
        Storage storage = new Storage();
        List<FlowerType> flowerType = flowerTypeService.findAll();
        model.addAttribute("flower", flower);
        model.addAttribute("storage", storage);
        model.addAttribute("flowerTypeList", flowerType);
        return PagePath.ADD_ITEM;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addItem")
    public String addItemPost(@ModelAttribute("flower") Flower flower,
                              @ModelAttribute("storage") Storage storage,
                              @ModelAttribute("image") MultipartFile image,
                              Model model){
        if (flower.getPrice() < 1 || flower.getWatering() < 1 || storage.getCount() < 1) {
            model.addAttribute("wrongInput", true);
            return PagePath.ADD_ITEM;
        }
        storage.setFlower(flower);
        storageService.save(storage);
        try {
            if (image != null) {
                byte[] bytes = image.getBytes();
                flower.setFlowerImage(flower.getId() + ".png");
                flowerService.save(flower);
                String name = flower.getId() + ".png";
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(PATHNAME + name)));
                stream.write(bytes);
                stream.close();
            }
        } catch (Exception e) {
            logger.error("Wrong file for item image", e);
        }
        logger.info("Add Item success");
        return PagePath.FLOWER_LIST_REDIRECT;
    }
}
