package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.model.Storage;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.repository.StorageRepository;
import by.panasenko.flowershop.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageRepository storageRepository;

    public Storage save(Storage storage){
        return storageRepository.save(storage);
    }

    public Storage findByFlower (Flower flower) {
        return storageRepository.findByFlower(flower);
    }
}
