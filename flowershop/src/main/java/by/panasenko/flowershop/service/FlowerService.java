package by.panasenko.flowershop.service;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.product.AjaxItem;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.FlowerPageCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public interface FlowerService {
    Flower save(Flower flower);
    Flower findOne(Integer id) throws ShopException;
    void deleteFlower(Integer id);
    Page<Flower> findAll(FlowerPageCriteria flowerPageCriteria);
    Pageable buildPage(FlowerPageCriteria flowerPageCriteria);
    void fillModel(FlowerPageCriteria flowerPageCriteria, Page<Flower> page, Model model);
    List<AjaxItem> findByKeyword(String keyword);
}
