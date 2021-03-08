package com.flowershop.service.impl;

import com.flowershop.model.FlowerType;
import com.flowershop.repository.FlowerTypeRepository;
import com.flowershop.service.FlowerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerTypeServiceImpl implements FlowerTypeService {
    @Autowired
    private FlowerTypeRepository flowerTypeRepository;

    public FlowerType save(FlowerType flowerType){
        return flowerTypeRepository.save(flowerType);
    }

    public List<FlowerType> findAll() {
        return flowerTypeRepository.findAll();
    }

    public FlowerType getOne(Integer id) {
        return flowerTypeRepository.getOne(id);
    }
}
