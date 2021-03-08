package com.flowershop.service;

import com.flowershop.model.Storage;
import com.flowershop.model.product.Flower;

public interface StorageService {
    Storage save(Storage storage);
    Storage findByFlower (Flower flower);
}
