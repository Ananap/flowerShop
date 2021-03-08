package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.Storage;
import by.panasenko.flowershop.model.product.Flower;
import org.springframework.data.repository.CrudRepository;

public interface StorageRepository extends CrudRepository<Storage, Integer> {
    Storage findByFlower(Flower flower);
}
