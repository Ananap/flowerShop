package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.Storage;
import by.panasenko.flowershop.model.product.Flower;

public interface StorageService {
    Storage save(Storage storage);
    Storage findByFlower (Flower flower);
}
