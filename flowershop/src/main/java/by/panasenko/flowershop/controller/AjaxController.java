package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.model.product.AjaxItem;
import by.panasenko.flowershop.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AjaxController {
    @Autowired
    public FlowerService flowerService;

    @RequestMapping("/findItem")
    public @ResponseBody List<AjaxItem> findItem(@RequestParam("CHARS") String chars) {
        return flowerService.findByKeyword(chars);
    }
}
