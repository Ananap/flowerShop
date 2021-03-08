package com.flowershop.controller;

import com.flowershop.exception.ShopException;
import com.flowershop.model.FlowerType;
import com.flowershop.model.Storage;
import com.flowershop.model.product.Flower;
import com.flowershop.model.product.FlowerPageCriteria;
import com.flowershop.model.product.FlowerSearchCriteria;
import com.flowershop.service.FlowerService;
import com.flowershop.service.FlowerTypeService;
import com.flowershop.service.StorageService;
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
import java.util.List;

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
    private StorageService storageService;

    @GetMapping("/allOrderInfo")
    public String allOrderInfo () {
        //todo sorting, pagination and change status function
        return "admin/orderInfo";
    }

    @GetMapping("/addItem")
    public String addItem(Model model) {
        Flower flower = new Flower();
        Storage storage = new Storage();
        List<FlowerType> flowerType = flowerTypeService.findAll();
        model.addAttribute("flower", flower);
        model.addAttribute("storage", storage);
        model.addAttribute("flowerTypeList", flowerType);
        return "admin/addItem";
    }

    @PostMapping("/addItem")
    public String addItemPost(@ModelAttribute("flower") Flower flower,
                              @ModelAttribute("storage") Storage storage,
                              @ModelAttribute("image") MultipartFile image,
                              Model model){
        if (flower.getPrice() < 1 || flower.getWatering() < 1 || storage.getCount() < 1) {
            model.addAttribute("wrongInput", true);
            return "admin/addItem";
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
        return "redirect:flowerList";
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
        return "admin/flowerList";
    }

    @GetMapping("/itemInfo")
    public String flowerInfo(@RequestParam("id") Integer id, Model model) throws ShopException {
        Flower flower = flowerService.findOne(id);
        model.addAttribute("flower", flower);
        return "admin/itemInfo";
    }

    @GetMapping("/updateItem")
    public String updateItem(@RequestParam("id") Integer id, Model model) throws ShopException {
        Flower flower = flowerService.findOne(id);
        model.addAttribute("flower", flower);
        model.addAttribute("flowerTypeList", flowerTypeService.findAll());
        return "admin/updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItemPost(@ModelAttribute("flower") Flower flower,
                                 @ModelAttribute("image") MultipartFile image) throws ShopException {
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
        return "redirect:flowerList";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("id") Integer id) {
        flowerService.deleteFlower(id);
        return "redirect:flowerList";
    }
}
