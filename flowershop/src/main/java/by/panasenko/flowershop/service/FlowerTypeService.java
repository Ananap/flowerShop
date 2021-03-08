package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.FlowerType;

import java.util.List;

public interface FlowerTypeService {
    FlowerType save(FlowerType flowerType);
    List<FlowerType> findAll();
    FlowerType getOne(Integer id);
}
