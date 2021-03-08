package com.flowershop.service.impl;

import com.flowershop.model.Storage;
import com.flowershop.model.product.Flower;
import com.flowershop.repository.StorageRepository;
import com.flowershop.service.StorageService;
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
