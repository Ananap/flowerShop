package com.flowershop.repository;

import com.flowershop.model.Storage;
import com.flowershop.model.product.Flower;
import org.springframework.data.repository.CrudRepository;

public interface StorageRepository extends CrudRepository<Storage, Integer> {
    Storage findByFlower(Flower flower);
}
