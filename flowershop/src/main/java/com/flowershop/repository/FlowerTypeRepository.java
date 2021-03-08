package com.flowershop.repository;

import com.flowershop.model.FlowerType;
import com.flowershop.model.product.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerTypeRepository extends JpaRepository<FlowerType, Integer> {
    FlowerType findByFlower(Flower flower);

    @Override
    FlowerType getOne(Integer integer);
}
