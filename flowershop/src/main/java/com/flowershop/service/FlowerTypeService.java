package com.flowershop.service;

import com.flowershop.model.FlowerType;

import java.util.List;

public interface FlowerTypeService {
    FlowerType save(FlowerType flowerType);
    List<FlowerType> findAll();
    FlowerType getOne(Integer id);
}
