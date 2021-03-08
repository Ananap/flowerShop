package com.flowershop.service;

import com.flowershop.model.product.Flower;
import com.flowershop.model.product.FlowerPageCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface FlowerService {
    Flower save(Flower flower);
    Flower findOne(Integer id);
    void deleteFlower(Integer id);
    Page<Flower> findAll(FlowerPageCriteria flowerPageCriteria);
    Pageable buildPage(FlowerPageCriteria flowerPageCriteria);
    void fillModel(FlowerPageCriteria flowerPageCriteria, Page<Flower> page, Model model);

}
